package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Feladat {
    private ArrayList<Taxi> lista;

    public Feladat()
    {
        lista = Beolvasas();
        kiiratas();
        taxi(6185);
        osszMerfold();
        leghosszabfuvarAdatai();
        legbokezubbutasAdatai();
        legtobbetVezetettTaxis(4261);
        hibasSorok();
        letezoTaxi(1452);
        haromLegrovidebbUtazas();
        karacsonyiFuvarokSzama();
        borravaloAranya();
    }

    private ArrayList<Taxi> Beolvasas()
    {
        ArrayList<Taxi> back = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("fuvar.csv"));
            String sor = br.readLine();
            sor = br.readLine();
            while (sor != null) {
                back.add(new Taxi(sor));
                sor = br.readLine();
            }
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return back;
    }

    private void kiiratas()
    {
        System.out.println("1. Feladat:");
        lista.stream().forEach(t -> {
            System.out.println(t);
        });
        System.out.println("Összesen: "+lista.stream().count()+" fuvar");
    }

    private void taxi(int id)
    {
        Double bevetel = lista.stream()
                .filter(t-> t.getAzon()==id)
                .mapToDouble(t-> t.getViteldij())
                .sum();
        long fuvarok = lista.stream()
                .filter(t-> t.getAzon()==id)
                .count();
        System.out.println("2.feladat: "+fuvarok+" fuvar alatt: "+bevetel+"$");
    }

    private void osszMerfold()
    {
        Double tavolsag = lista.stream()
                .mapToDouble(t-> t.getMegtettTavolsag())
                .sum();
        System.out.println("3.feladat: Összesen megtett mérföldek száma: "+tavolsag);
    }

    public void leghosszabfuvarAdatai()
    {

        System.out.println("4.feladat: A leghosszab fuvar");
        lista.stream()
                .sorted((a, b)-> -1*Integer.compare(a.getIdotartam(), b.getIdotartam()))
                .limit(1)
                .forEach(i-> System.out.println(String.format("\tFuvar hossza: %d másodperc\n" +
                                "\tTaxi azonosító: %d\n" +
                                "\tMegtett tárloság: %.1f mérföld\n" +
                                "\tFuvar indulási ideje:%s\n" +
                                "\tViteldíj: %.3f$\n" +
                                "\tBorravaló: %.3f$\n" +
                                "\tFizetési mós: %s\n"
                        , i.getIdotartam(), i.getAzon(), i.getMegtettTavolsag(), i.getIndulasIdo(), i.getViteldij(), i.getBorravalo(), i.getFizMod())));
    }

    public void legbokezubbutasAdatai()
    {

        System.out.println("5.feladat: A legbőkezúbb utas");
        lista.stream()
                .sorted((a, b)-> Double.compare(a.getViteldij() / a.getBorravalo(), b.getViteldij() / b.getBorravalo()))
                .limit(1)
                .forEach(i-> System.out.println(String.format("\tFuvar hossza: %d másodperc\n" +
                                "\tTaxi azonosító: %d\n" +
                                "\tMegtett tárloság: %.1f mérföld\n" +
                                "\tFuvar indulási ideje:%s\n" +
                                "\tViteldíj: %.3f$\n" +
                                "\tBorravaló: %.3f$\n" +
                                "\tFizetési mós: %s\n"
                        , i.getIdotartam(), i.getAzon(), i.getMegtettTavolsag(), i.getIndulasIdo(), i.getViteldij(), i.getBorravalo(), i.getFizMod())));
    }

    private void legtobbetVezetettTaxis(int id)
    {
        Double tavolsag = lista.stream()
                .filter(t-> t.getAzon()==id)
                .mapToDouble(t-> t.getMegtettTavolsag())
                .sum();
        System.out.printf("6.feladat: %d taxi összesen: %.3f km-t ment\n\n", id, tavolsag*1.6);
    }

    private void hibasSorok()
    {
        System.out.println("7. Feladat");
        List<Taxi> szurtLista = lista.stream()
                .filter(t -> t.getIdotartam() > 0 && t.getViteldij() > 0 && t.getMegtettTavolsag() == 0)
                .collect(Collectors.toList());
        System.out.println("Hibás sorok száma:" + szurtLista.stream().count());
        System.out.println("Hibás sorok összes időtartama:" + szurtLista.stream()
                .mapToDouble(i -> i.getIdotartam())
                .sum()+"mp");
        System.out.println("Hibás sorok teljes bevétele:" + szurtLista.stream()
                .mapToDouble(i -> i.getViteldij() + i.getBorravalo())
                .sum() + "$");
    }

    private void letezoTaxi(int id)
    {
        boolean e = lista.stream().anyMatch(i -> i.getAzon() == id);
        System.out.println("8.Feladat:\n\ttaxi ("+id+"): "+ (e ? "létezik\n" : "nem létezik\n"));
    }

    private void haromLegrovidebbUtazas()
    {
        System.out.println("9.Feladat");
        lista.stream()
                .sorted((a, b)-> Double.compare(a.getIdotartam(), b.getIdotartam()))
                .filter(i -> i.getIdotartam() > 0)
                .limit(3)
                .forEach(i-> System.out.println(String.format("\tFuvar hossza: %d másodperc\n" +
                                "\tTaxi azonosító: %d\n" +
                                "\tMegtett tárloság: %.1f mérföld\n" +
                                "\tFuvar indulási ideje:%s\n" +
                                "\tViteldíj: %.3f$\n" +
                                "\tBorravaló: %.3f$\n" +
                                "\tFizetési mós: %s\n"
                        , i.getIdotartam(), i.getAzon(), i.getMegtettTavolsag(), i.getIndulasIdo(), i.getViteldij(), i.getBorravalo(), i.getFizMod())));
    }

    private void karacsonyiFuvarokSzama()
    {
        System.out.println("10.Feladat");
        System.out.println(lista.stream()
                .filter(n -> n.getIndulasIdo().contains("-12-24"))
                .count()+" fuvar volt December 24.-én\n");
    }

    private void borravaloAranya()
    {
        List<Taxi> nap = lista.stream()
                .filter(t -> t.getIndulasIdo().contains("-12-31"))
                .collect(Collectors.toList());

        List<Taxi> jo = nap.stream()
                .filter(f -> f.getBorravalo() > 0)
                .collect(Collectors.toList());
        System.out.println("11. Feladat");
        System.out.println("\tFuvarok száma: "+nap.stream().count());
        System.out.printf("\tNagylelkű: %d utas > %.3f$ | %.2f [százalék]\n",
                jo.stream().count(), jo.stream().mapToDouble(d -> d.getBorravalo()).sum(),(double) jo.stream().count()/nap.stream().count()*100.0);
        System.out.printf("\tFösvény: %d utas > %.3f$ | %.2f [százalék]",
                nap.stream().count()-jo.stream().count(),
                nap.stream().mapToDouble(d -> d.getBorravalo()).sum()-jo.stream().mapToDouble(d -> d.getBorravalo()).sum(),
                100 - (double) jo.stream().count()/nap.stream().count()*100.0);
    }
}


