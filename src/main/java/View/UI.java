package View;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class UI {

    private String name;
    protected UI parentUI;
    private HashMap<Integer, UI> subMenus = new HashMap<Integer, UI>();
    protected static Scanner scanner;

    protected static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    public UI(String name, UI parentUI) {
        this.name = name;
        this.parentUI = parentUI;
    }

    public static void setScanner(Scanner scanner) {
        UI.scanner = scanner;
    }

    public void addToSubMenus(int UINumber, UI ui) {
        subMenus.put(UINumber, ui);
    }

    public void show() {
        System.out.println(this.name + ':');
        for (int UINumber : subMenus.keySet())
            System.out.println(UINumber + ") " + subMenus.get(UINumber).name);
        if (parentUI == null)
            System.out.println(Integer.toString(this.subMenus.size() + 1) + ") exit");
        else
            System.out.println(Integer.toString(this.subMenus.size() + 1) + ") back");
    }

    public void execute(){
        int i;
        try {
            String input = scanner.nextLine();
            i = Integer.parseInt(input);
            if (i>this.subMenus.size() + 1)
                throw new Exception("invalid input");
            if (i<1){
                throw new Exception("invalid input");
            }
        }catch (Exception e){
            System.out.println("your input is invalid");
            this.execute();
            return;
        }
        if (i == this.subMenus.size() + 1){
            if (this.parentUI == null){

                // in ja bayad data roo file berize fek konam !
                System.exit(1);
            }
            else {
                parentUI.show();
                parentUI.execute();
            }
        }
        else{
            this.subMenus.get(i).show();
            this.subMenus.get(i).execute();
        }
    }

    public abstract void run();
}
