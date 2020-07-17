package Server.Model.Sorts;

import Server.Model.Request.Request;

import java.util.Comparator;

public abstract class RequestsSort implements Comparator {
    public static class requestSortById implements Comparator<Request>{
        public int compare(Request firstOne , Request secondOne){
            return firstOne.getRequestId().compareTo(secondOne.getRequestId());
        }
    }

    public static class requestSortByType implements Comparator<Request>{
        public int compare(Request firstOne , Request secondOne){
            return firstOne.getType().compareToIgnoreCase(secondOne.getType());
        }
    }
}
