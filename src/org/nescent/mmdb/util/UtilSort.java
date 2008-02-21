package org.nescent.mmdb.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import org.nescent.mmdb.hibernate.dao.MmCvTerm;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyAttrCvtermAssoc;

@SuppressWarnings("unchecked")
public class UtilSort {

    Collection sortedCollection;
    Collection collection;

    public Collection getCollection() {
	return collection;
    }

    public void setCollection(Collection collection) {
	this.collection = collection;
	Comparable[] objs = new Comparable[collection.size()];

	if (sortedCollection == null)
	    sortedCollection = new ArrayList();

	sortedCollection.clear();
	int count = 0;
	for (Object o : collection) {
	    objs[count++] = (Comparable) o;
	}

	for (int i = 0; i < objs.length - 1; i++) {
	    for (int j = i + 1; j < objs.length; j++) {
		if (objs[i].compareTo(objs[j]) < 0) {
		    Comparable tmp = objs[i];
		    objs[i] = objs[j];
		    objs[j] = tmp;
		}
	    }
	}

	for (int i = 0; i < objs.length; i++) {
	    sortedCollection.add(objs[i]);
	}
    }

    public Collection getSortedCollection() {
	return sortedCollection;
    }

    public void setSortedCollection(SortedSet sortedCollection) {
	this.sortedCollection = sortedCollection;
    }

    public static void main(String[] agrs) {
	List list = new ArrayList();
	MmCvTerm t1 = new MmCvTerm();
	t1.setName("x");
	MmExperimentStudyAttrCvtermAssoc assoc1 = new MmExperimentStudyAttrCvtermAssoc();
	assoc1.setMmCvTerm(t1);
	list.add(assoc1);

	MmCvTerm t2 = new MmCvTerm();
	t2.setName("t");
	MmExperimentStudyAttrCvtermAssoc assoc2 = new MmExperimentStudyAttrCvtermAssoc();
	assoc2.setMmCvTerm(t2);
	list.add(assoc2);

	MmCvTerm t3 = new MmCvTerm();
	t3.setName("a");
	MmExperimentStudyAttrCvtermAssoc assoc3 = new MmExperimentStudyAttrCvtermAssoc();
	assoc3.setMmCvTerm(t3);
	list.add(assoc3);

	MmCvTerm t4 = new MmCvTerm();
	t4.setName("z");
	MmExperimentStudyAttrCvtermAssoc assoc4 = new MmExperimentStudyAttrCvtermAssoc();
	assoc4.setMmCvTerm(t4);
	list.add(assoc4);

	UtilSort sort = new UtilSort();
	sort.setCollection(list);
	Collection set = sort.getSortedCollection();
	for (Object o : set) {
	    MmExperimentStudyAttrCvtermAssoc assoc = (MmExperimentStudyAttrCvtermAssoc) o;
	    System.out.println(assoc.getMmCvTerm().getName());
	}

    }
}
