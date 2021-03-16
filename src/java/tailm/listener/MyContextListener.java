/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.listener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author DELL INC
 */
public class MyContextListener implements ServletContextListener{

    
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
         FileReader fr = null;
        BufferedReader br = null;
        Map<String, String> filter = new HashMap<>();
        try {
            String file = sce.getServletContext().getRealPath("/WEB-INF/filter.txt");
            fr = new FileReader(file);

            br = new BufferedReader(fr);
            String line = "";
            while ((line = br.readLine()) != null) {
                StringTokenizer stk = new StringTokenizer(line, "=");
                String key = stk.nextToken();
                String value = stk.nextToken();
                filter.put(key, value);
            }
            sce.getServletContext().setAttribute("FILTER", filter);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(MyContextListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("FILTER");
    }
    
}
