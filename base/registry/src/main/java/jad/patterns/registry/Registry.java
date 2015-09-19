package jad.patterns.registry;

/**
 * Created by jdonofrio on 9/18/15.
 */
public class Registry {
    private static Registry instance;

    private Registry(){};

    public static Registry getInstance(){
        if(instance == null){
            instance = new Registry();
        }
        return instance;
    }
}
