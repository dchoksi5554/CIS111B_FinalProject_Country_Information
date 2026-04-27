public class Country {
    public Name name;
    public String[] capital;
    public int population;
    public int area;
    public String[] timezones;
    public Idd idd;
    public Languages languages;
    public Currencies currencies;
}

class Name {
    public String common;
    public String official;
}

class Idd {
    public String root;
}

class Languages {
    public String eng;
}


class Currencies {
    public CurrencyInfo USD;

}

class CurrencyInfo {
    public String name;
    public String symbol;
}

