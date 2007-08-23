/**
 * Record: abstract class with unique id 
 */
package org.nescent.mmdb.util;

/**
 * @author xianhua
 *
 */

public abstract class Record {
	/**
	 * id: unique id of the record
	 */
	int id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
