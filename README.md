

useful links
====

* example for multiple tables

http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/


* example for upgrade

http://stackoverflow.com/questions/8133597/android-upgrading-db-version-and-adding-new-table


        switch (view.getId()) {
            case R.id.myButton_save:
                // TODO: 寫出保存的邏輯順序
                // 新方法 getGongYSMC_ID 取供應商 id, 如果沒找到 要 return -1 過來
                long old_id = getGongYSMC_ID(new String[] {mEditText_gongYSMC.getText().toString()});
                if (old_id > -1 && old_id != _id) { // 找到了供應商 並且 不等於現在編輯的 _id
                    // 供應商名稱已經用過, 不管怎樣就是跳出提醒不給保存
                } else if (_id == -1) { // 新增
                    // insert
                } else {
                    // update
                }

                ... (底下略)
