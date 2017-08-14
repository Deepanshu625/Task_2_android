/*
 * Copyright (C) 2016 The Android Open Source Project
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
package com.app.task_2_android_app;;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class Show_fragment extends Fragment {



     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.display, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<data> arrayList = new ArrayList<data>();
        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures/Camera_App";
        File mediaStorageDir = new File("/sdcard/Pictures/Camera_App");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
            }
        }
        File f = new File(path);
        File file[] = f.listFiles();
        Log.e("FOLDER", "New folder");
        if(file==null)
        {
            arrayList.add(new data("NO Image clicked yet....."));
        }
        else {
            for (int i = 0; i < file.length; i++) {

                //System.out.println(" "+file[i]);
                String str = file[i].toString();
                int a = str.lastIndexOf('/') + 1;
                int b = str.length();
                String sub = str.substring(a, b);
                arrayList.add(new data(sub));
                Log.e("FOLDER", "" + sub);
            }
        }
        ListView listView = (ListView) view.findViewById(R.id.list);
        data_adapter adapter = new data_adapter(getActivity(),arrayList);
        listView.setAdapter(adapter);
    }
}
