
/*
 * Copyright (c) 2016. . The Km2Labs Project
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

package com.enfle.spendview.core.view;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class Typefaces {

	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	public static Typeface get(Context c, String name) {
		
		synchronized (cache) {
			
			if (!cache.containsKey(name)) {
				
				Typeface t = Typeface.createFromAsset(c.getAssets(), name);
				
				cache.put(name, t);
			}
			
			return cache.get(name);
		}
	}

}