

useful links
====

* example for multiple tables

http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/


* example for upgrade

http://stackoverflow.com/questions/8133597/android-upgrading-db-version-and-adding-new-table


        switch (view.getId()) {
            case R.id.myButton_save:
                // TODO: 寫出保存的邏輯順序
                boolean isUsed = checkGongYSMCisUsed(new String[] {mEditText_gongYSMC.getText().toString()});
                if (isUsed) {
                    // 供應商名稱已經用過, 不管怎樣就是跳出提醒不給保存
                } else if (_id == -1) { // 新增
                    // insert
                } else {
                    // update
                }

                ... (底下略)
