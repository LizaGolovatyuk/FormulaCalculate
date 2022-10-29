package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;


class FormulaTest {
    Formula f= new Formula();

    @BeforeEach
    void setUp() {
        String str="(((2*12)/3)+5)";
        f.setFormula(str);

    }

    @Test
    void testIsCorrectFormula() {
        setUp();
        boolean tmp=f.isCorrect();
        Assertions.assertEquals(tmp,true);
    }

    @Test
    void testPostfixFormula() {
        setUp();
        Queue<String> tmp=f.postfixFormula();
        String tmp_s1="";
        String tmp_s2="2.012.0*3.0/5.0+";
        while(!tmp.isEmpty()) {
            tmp_s1+=tmp.peek();
            tmp.remove();
        }
        Assertions.assertEquals(tmp_s1,tmp_s2);
    }

    @Test
    void testCalculateFormula() {
        setUp();
        String tmp1=f.calculateFormula();
        String tmp2="13.0";
        Assertions.assertEquals(tmp1,tmp2);
    }
}