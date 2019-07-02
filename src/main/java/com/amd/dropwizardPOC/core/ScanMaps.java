package com.amd.dropwizardPOC.core;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



@Entity
@Table(name = "scan_maps")
@NamedQueries({
		@NamedQuery(name = "com.amd.dropwizardPOC.core.ScanMaps.findAll", query = "SELECT s FROM ScanMaps s") })

public class ScanMaps {
	
	public ScanMaps() {
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	private int id;
	
	@Column(name="table_name")
	private String tableName;
	
	@Column(name="family")
	private String family;
	
	@Column(name="major_rev")
	private String majorRevision;
	
	@Column(name="minor_rev")
	private int minorRevision;
	
	@Column(name="friendly_name")
	private String friendlyName;
	
	@Column(name="max_scan_dumps")
	private short maxscandumps;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getMajorRevision() {
		return majorRevision;
	}

	public void setMajorRevision(String majorRevision) {
		this.majorRevision = majorRevision;
	}

	public int getMinorRevision() {
		return minorRevision;
	}

	public void setMinorRevision(int minorRevision) {
		this.minorRevision = minorRevision;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public short getMaxscandumps() {
		return maxscandumps;
	}

	public void setMaxscandumps(Short maxscandumps) {
		this.maxscandumps = maxscandumps;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (!(o instanceof User)) {
	            return false;
	        }

	        final ScanMaps that = (ScanMaps) o;

	        return Objects.equals(this.id, that.id) &&
	                Objects.equals(this.tableName, that.tableName) &&
	                Objects.equals(this.family, that.family) &&
	                Objects.equals(this.majorRevision, that.majorRevision) &&
	                Objects.equals(this.minorRevision, that.minorRevision) &&
	                Objects.equals(this.friendlyName, that.friendlyName) &&
	                Objects.equals(this.maxscandumps, that.maxscandumps);
	    }

//	    @Override
//	    public int hashCode() {
//	        return Objects.hash(id, , );
//	    }
}
