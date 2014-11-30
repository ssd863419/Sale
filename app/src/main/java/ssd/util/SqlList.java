package ssd.util;

import java.util.ArrayList;

/**
 * Created by 0_o on 2014/11/29.
 */
public class SqlList extends ArrayList {

   public SqlMap getMyMap(int index)  {
       return (SqlMap) this.get(index);
   }

   public String[][] toSpinnerArray(String value, String label) {
       String[][] result = new String[this.size()][2];
       for (int i = 0; i < this.size(); i++) {
           result[i][0] = this.getMyMap(i).getString(value);
           result[i][1] = this.getMyMap(i).getString(label);
       }
       return result;
   }
}
