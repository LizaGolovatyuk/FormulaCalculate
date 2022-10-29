package org.example;

public class Main {
    public static void main(String[] args) {
        String f="((((2+x)^2)-(2*z))+cost+(100/20))";
        Formula formula = new Formula();
        formula.setFormula(f);
        if(formula.isCorrect()){
        String res=formula.calculateFormula();
        System.out.print(res+"\n");
        }
        else  System.out.print("Формула составлена некорректно \n");
    }
}