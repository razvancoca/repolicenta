package utils;


import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ServiceProgressIndicator extends Service<String> {
	protected Task<String> createTask() {
	    return new Task<String>() {
	        @Override
	        protected String call() throws Exception {
	            //DO YOU HARD STUFF HERE
	            String res = "toto";
	            Thread.sleep(5000);
	            return res;
	        }
	    };
	}
}