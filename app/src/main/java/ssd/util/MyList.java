package ssd.util;

import java.util.ArrayList;

/**
 * Created by 0_o on 2014/11/29.
 */
public class MyList extends ArrayList {

   public MyMap getMyMap(int index)  {
       return (MyMap) this.get(index);
   }
}
