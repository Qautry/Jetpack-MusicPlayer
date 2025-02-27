/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kunminx.puremusic.ui.state;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Create by KunMinX at 19/10/29
 */
public class MainActivityViewModel extends ViewModel {

  public final ObservableBoolean isDrawerOpened = new ObservableBoolean();

  public final MutableLiveData<Boolean> openDrawer = new MutableLiveData<>(false);

  public final MutableLiveData<Boolean> allowDrawerOpen = new MutableLiveData<>(true);

}
