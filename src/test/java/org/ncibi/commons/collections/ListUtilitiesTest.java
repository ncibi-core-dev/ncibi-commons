package org.ncibi.commons.collections;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListUtilitiesTest
{
    @Test
    public void testSplitIntoChunks()
    {
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        
        List<List<Integer>> chunks = ListUtilities.splitIntoChunks(list, 3);
        
        System.out.println(chunks.size());
        int index = 0;
        for (List<Integer> l : chunks)
        {
            for (Integer i : l)
            {
                System.out.println("chunks[" + index + "] = " + i);
            }
            index++;
        }
    }
}
