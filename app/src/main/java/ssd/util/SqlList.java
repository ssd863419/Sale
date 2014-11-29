package ssd.util;

import java.util.ArrayList;

/**
 * Created by 0_o on 2014/11/29.
 */
public class SqlList extends ArrayList {

   public SqlMap getMyMap(int index)  {
       return (SqlMap) this.get(index);
   }
}
