package Model.Sorts;

import Model.Request.Request;

import java.util.Comparator;

public abstract class RequestsSort implements Comparator {
    public static class requestSortById implements Comparator<Request>{
        public int compare(Request one , Request two){
            return one.getRequestId().compareTo(two.getRequestId());
        }
    }

    public static class requestSortByType implements Comparator<Request>{
        public int compare(Request one , Request two){
            return one.getType().compareToIgnoreCase(two.getType());
        }
    }
}
