package org.example;

import org.junit.jupiter.api.Test;
import java.lang.Math;

import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.LinkedList;

/**
 * Класс, который хранит формулу в виде строки и имеет возможность pассчитывать эту формулу
 */
public class Formula {
    private String formula;

    /**
     * Конструктор с параметром
     * @param formula - формула в виде строки
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }


    /**
     * Метод проверяет корректно ли расставлены скобки в формуле
     * @return true в случае правильной расстановки скобок, false в инном случае
     */
    public boolean isCorrect() {
        boolean res = true;
        int size = formula.length();
        String word;
        Stack<String> formula_stack = new Stack<>();
        int i1 = 0, i2 = 1;

        while (size > 0 && res) {
            word = formula.substring(i1, i2);
            if (word.equals("(")) formula_stack.push(word);
            else {
                if (word.equals(")") && formula_stack.size() > 0 && formula_stack.peek().equals("(")) formula_stack.pop();
                else {
                    if (word.equals(")") && formula_stack.size() > 0 && formula_stack.peek().equals(")")) res = false;
                    if (word.equals(")") && formula_stack.size() == 0) res = false;
                }
            }
            size--;
            i1++;
            i2++;
        }
        return res && formula_stack.isEmpty();
    }

    /**
     * Метод переводит стандартные функции в число
     * @param funcX - стандартная функция
     * @return число - посчитанная функция
     */
    private  double convertFuncX(String funcX) {
        double tmp_x, res=0;
        Scanner scan = new Scanner(System.in);
        String func =funcX.substring(0,funcX.length()-1);
        String x=funcX.substring(funcX.length()-1);
        System.out.print("значение неизвестной переменной " + x + ":");
        tmp_x = scan.nextDouble();
        switch (func){
            case "sin": {
                res=Math.sin(tmp_x);
               break;
            }
            case "cos": {
                res=Math.cos(tmp_x);
                break;
            }
            case "tg": {
                res=Math.tan(tmp_x);
                break;
            }
            case "ctg": {
                res=1/Math.tan(tmp_x);
                break;
            }
            case "log": {
                res=Math.log(tmp_x);
                break;
            }
         }
         return res;
    }

    /**
     * Метод переводит переменную в число
     * @param x - переменная
     * @return число, подставленное вместо переменной
     */
    private  double convertX(String x){
        double tmp_x=0;
        Scanner scan = new Scanner(System.in);
        System.out.print("Введите значение неизвестной переменной " + x + ":");
        tmp_x = scan.nextDouble();
        return tmp_x;
    }

    /**
     * Метод
     * @param digit - число в виде строки
     * @return число
     */
    private  double convertDigit(String digit){
        double res=0;
        res = Double.parseDouble(digit);
        return res;
    }

    /**
     * Метод вычисляет операцию
     * @param oper1 - первый операнд
     * @param oper2 - второй операнд
     * @param op - оперция
     * @return число - результат операции
     */
    private double calculate(String oper1, String oper2, String op) {
        //Scanner scan = new Scanner(System.in);
        double oper1_ = convertDigit(oper1);
        double oper2_ = convertDigit(oper2);
        double res= 0;
        switch (op) {
            case "+": {
                res = oper1_ + oper2_;
                break;
            }
            case "-": {
                res = oper1_ - oper2_;
                break;
            }
            case "*": {
                res = oper1_ * oper2_;
                break;
            }
            case "/": {
                res = oper1_ / oper2_;
                break;
            }
            case "^": {
                res =Math.pow(oper1_ , oper2_);
                break;
            }
            default: {
                break;
            }
        }
        return res;
    }

    /**
     * Метод переводит формулу в постфиксную вид и заменяет выражения и буквы на числа
     * @return очередь, которая хранит формулу в постфиксном виде
     */
    public Queue<String> postfixFormula() {
        int size = formula.length();
        double res=0;
        Character character;
        String func="";
        String number = "";
        Stack<String> res_stack = new Stack<String>();
        Queue<String> res_queue= new LinkedList<String>();
        int i1 = 0;

        while (size > 0) {
            character = formula.charAt(i1);

            if (Character.isLetter(character)) {
                while ((size > 0)&&(Character.isLetter(character))) {
                    func += character;
                    size--;
                    i1++;
                    if(size>0) character = formula.charAt(i1);
                }
                size++;
                i1--;

                if(func.length()>=2) res=convertFuncX(func);
                else res=convertX(func);

                func=String.valueOf(res);
                res_queue.add(func);
                func="";
            }
            else
                if (Character.isDigit(character)) {
                while ((size > 0)&&(Character.isDigit(character))) {
                    number += character;
                    size--;
                    i1++;
                    if(size>0) character = formula.charAt(i1);
                }
                size++;
                i1--;

                res=convertDigit(number);
                number=String.valueOf(res);

                res_queue.add(number);
                number="";
            }
            else {
                if (!character.equals(')')) res_stack.push(String.valueOf(character));
                else {
                    String tmp = res_stack.peek();
                    while (!tmp.equals("(")) {
                        res_queue.add(tmp);
                        res_stack.pop();
                        tmp = res_stack.peek();
                    }
                    res_stack.pop();
                }
            }
            size--;
            i1++;
        }
        while (!res_stack.isEmpty()) {
            String tmp = res_stack.peek();
            res_queue.add(tmp);
            res_stack.pop();
        }
        return res_queue;
    }

    /**
     * Метод подсчитывает значение формулы
     * @return строку - результат подсчета формулы
     */
    public String calculateFormula() {
            String oper1, oper2;
            String word = "";
            Stack<String> q = new Stack<String>();
            Queue<String> tmp = new LinkedList<String>();
            tmp = postfixFormula();
            double res = 0;

            while (!tmp.isEmpty()) {
                word = tmp.peek();
                tmp.remove();
                Character character = word.charAt(0);
                Character character1 = '%';
                if (word.length() > 1) character1 = word.charAt(1);
                if (Character.isDigit(character) || character.equals('0')) q.add(word);
                else {
                    if (character.equals('-') && Character.isDigit(character1)) q.add(word);
                    else {
                        if (character.equals('-') && character1.equals('0')) q.add(word);
                        else {
                            oper2 = q.peek();
                            q.pop();
                            oper1 = q.peek();
                            q.pop();
                            res = calculate(oper1, oper2, word);
                            String res_string = String.valueOf(res);
                            q.add(res_string);
                        }
                    }
                }
            }
            return q.peek();
    }

}





