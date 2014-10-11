/*
 * Copyright (C) 2008 Romain Guy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lza.pad.ui.adapter;

import android.database.CharArrayBuffer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lza.pad.ui.drawable.CrossFadeDrawable;

class BookViewHolder {
    TextView title;
    String bookId;
    CrossFadeDrawable transition;
    final CharArrayBuffer buffer = new CharArrayBuffer(64);
    boolean queryCover;
    String sortTitle;
    ImageView imgView;
    ImageView imgView2;
    LinearLayout container;
}
