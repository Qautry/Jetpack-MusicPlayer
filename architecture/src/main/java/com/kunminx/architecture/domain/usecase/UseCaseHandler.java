package com.kunminx.architecture.domain.usecase;


/**
 * Runs {@link UseCase}s using a {@link UseCaseScheduler}.
 */
public class UseCaseHandler {

  private static UseCaseHandler INSTANCE;

  private final UseCaseScheduler mUseCaseScheduler;

  public UseCaseHandler(UseCaseScheduler useCaseScheduler) {
    mUseCaseScheduler = useCaseScheduler;
  }

  public static UseCaseHandler getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
    }
    return INSTANCE;
  }

  public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> void execute(
          final UseCase<T, R> useCase, T values, UseCase.UseCaseCallback<R> callback) {
    useCase.setRequestValues(values);
    //noinspection unchecked
    useCase.setUseCaseCallback(new UiCallbackWrapper(callback, this));

    // The network request might be handled in a different thread so make sure
    // Espresso knows
    // that the app is busy until the response is handled.

    // This callback may be called twice, once for the cache and once for loading
    // the data from the server API, so we check before decrementing, otherwise
    // it throws "Counter has been corrupted!" exception.
    mUseCaseScheduler.execute(useCase::run);
  }

  private <V extends UseCase.ResponseValue> void notifyResponse(final V response,
                                                                final UseCase.UseCaseCallback<V> useCaseCallback) {
    mUseCaseScheduler.notifyResponse(response, useCaseCallback);
  }

  private <V extends UseCase.ResponseValue> void notifyError(
          final UseCase.UseCaseCallback<V> useCaseCallback) {
    mUseCaseScheduler.onError(useCaseCallback);
  }

  private static final class UiCallbackWrapper<V extends UseCase.ResponseValue> implements
          UseCase.UseCaseCallback<V> {
    private final UseCase.UseCaseCallback<V> mCallback;
    private final UseCaseHandler mUseCaseHandler;

    public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
                             UseCaseHandler useCaseHandler) {
      mCallback = callback;
      mUseCaseHandler = useCaseHandler;
    }

    @Override
    public void onSuccess(V response) {
      mUseCaseHandler.notifyResponse(response, mCallback);
    }

    @Override
    public void onError() {
      mUseCaseHandler.notifyError(mCallback);
    }
  }
}
