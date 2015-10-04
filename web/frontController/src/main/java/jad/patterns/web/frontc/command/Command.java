package jad.patterns.web.frontc.command;

import java.io.IOException;

public abstract class Command {
    public abstract void process() throws IOException;
    public abstract void processPost() throws IOException;
    protected void forward (){

    }
}