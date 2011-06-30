package org.ncibi.commons.closure;

import org.junit.Test;


public class ClosureTest
{
    @Test
    public void testFClosures()
    {   
        callF0(new F0<Void>()
                {
                    public Void apply()
                    {
                        System.out.println("Hello from F0");
                        return null;
                    }         
                });
        
        callP0(new P0()
        {
            public void apply()
            {
                System.out.println("Hello from P0");               
            }          
        });
    }
    
    public void callF0(F0<Void> f)
    {
        f.apply();
    }
    
    public void callP0(P0 p)
    {
        p.apply();
    }
}
