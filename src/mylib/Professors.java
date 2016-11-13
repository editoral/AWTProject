package mylib;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.LinkedList;


@ManagedBean
@SessionScoped
public class Professors{
    LinkedList<String> list;
    String property1;
    String property2;
    public Professors(){
	list = new LinkedList<String>();
	list.add("Endre Bangerter");
	list.add("Emmanuel Benoist");
	list.add("Ulrich Fiedler");
    }

    public LinkedList<String> getList(){
	return list;
    }
    public void setProperty1(String p){
	property1 = p;
    }
    public String getProperty1(){
	return property1;
    }

    public void setProperty2(String p){
	property2 = p;
    }
    public String getProperty2(){
	return property2;
    }


}