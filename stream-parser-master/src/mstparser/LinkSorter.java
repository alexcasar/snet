/**
 * LinkSorter class

 * Main attributes:
 *
 * Sorts ArrayList of links by two possibilities: position or score
 * Comparator methods in Link.java file
 *
 * @author		Andres Suarez, suarezandres@hotmail.com
 * @since		Sept 2019
 */
package mstparser;

import java.util.ArrayList;
import java.util.Collections;

public class LinkSorter {

  ArrayList<Link> link = new ArrayList<>();

  public LinkSorter(ArrayList<Link> link) {
    this.link = link;
  }

  public ArrayList<Link> getSortedLinkByPosition() {
    Collections.sort(link, Link.positionComparator);
    return link;
  }

  public ArrayList<Link> getSortedLinkByScore() {
    Collections.sort(link, Link.scoreComparator);
    return link;
  }
}
