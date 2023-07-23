
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author aaditya
 */
public class LoggingConfiguration 
{
    private static Logger logger=Logger.getLogger(LoggingConfiguration.class.getName());
    static{
        
        try {
            InputStream stream=LoggingConfiguration.class.getClassLoader().getResourceAsStream("logging.properties");
            LogManager.getLogManager().readConfiguration(stream);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
   
    
    
}
