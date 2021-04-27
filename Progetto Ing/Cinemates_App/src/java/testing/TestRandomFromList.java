package com.example.cinemates.testing;


import com.example.cinemates.classes.Utente;
import org.junit.Assert;
import org.junit.Test;

public class TestRandomFromList{

    @Test
    public void TestRandomFromListIdPositivoNumPositivoIdMaggiore(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(3,2)>=0);
    }

    @Test
    public void TestRandomFromListIdPositivoNumPositivoIdMinore(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(2,3)>=0);
    }

    @Test
    public void TestRandomFromListIdPositivoNumPositivoValoreUguale(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(3,3)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdPositivoNumNegativo(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(3,-3)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdPositivoNumZero(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(3,0)>=0);
    }

    @Test
    public void TestRandomFromListIdNegativoNumPositivo(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(-3,2)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdNegativoNumNegativoIdMinore(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(-3,-2)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdNegativoNumNegativoIdMaggiore(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(-1,-2)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdNegativoNumNegativoValoriUguali(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(-1,-1)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdNegativoNumZero(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(-3,0)>=0);
    }

    @Test
    public void TestRandomFromListIdZeroNumPositivo(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(0,2)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdZeroNumNegativo(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(0,-2)>=0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void TestRandomFromListIdZeroNumZero(){
        Utente utente = new Utente(null,null,null,null,null);
        Assert.assertTrue(utente.generateRandomFromList(0,0)>=0);
    }


}
