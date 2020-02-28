/**
 * MSTparser class

 * Main attributes:
 *
 * Define Link structure to use in parser
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 */
package mstparser;

import java.util.Comparator;

public class Link {
    private int li, ri; // left/right indexes of link
    private double score;

    // Constructor for a dummy link
    Link() {
        this(0, 0, 0);
    }

    // Class constructor
    Link(int left_index, int right_index, double score) {
        this.li = left_index;
        this.ri = right_index;
        this.score = score;
    }

    int getLi() {
        return li;
    }

    int getRi() {
        return ri;
    }

    double getScore() {
        return score;
    }

    // Ascending order
    public static Comparator<Link> positionComparator = new Comparator<Link>() {
        @Override
        public int compare(Link l1, Link l2) {
            return (-Integer.compare(l2.getLi(), l1.getLi())); // Negative symbol: ascending order
        }
    };

    // Descending order
    public static Comparator<Link> scoreComparator = new Comparator<Link>() {
        @Override
        public int compare(Link l1, Link l2) {
            return (Double.compare(l2.getScore(), l1.getScore()));
        }
    };

    @Override
    public String toString() {
        return "(" + this.li + "-" + this.ri + ", " + this.score + ")";
    }
}

