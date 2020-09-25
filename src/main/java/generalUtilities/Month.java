package generalUtilities;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public enum Month {
    BLANK(StringUtils.EMPTY, 0),
    JANUARY("january", 1),
    FEBRUARY("february", 2),
    MARCH("march", 3),
    APRIL("april", 4),
    MAY("may", 5),
    JUNE("june", 6),
    JULY("july", 7),
    AUGUST("august", 8),
    SEPTEMBER("september", 9),
    OCTOBER("october", 10),
    NOVEMBER("november", 11),
    DECEMBER("december", 12);

    private final String text;
    private final int value;

    Month(String text, int value){
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public int getValue() {
        return value;
    }

    public static Collection<Month> definedValues() {
        return Arrays.asList(Month.values());
    }

    public static Month fromString(String text) {
        for(Month month : definedValues()) {
            if(month.getText().contains(text.toLowerCase())){
                return month;
            }
        }

        return BLANK;
    }


}
