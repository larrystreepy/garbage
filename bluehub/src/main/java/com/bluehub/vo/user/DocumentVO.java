package com.bluehub.vo.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "document")
public class DocumentVO {

	@Id
	private int docId;
	private String docName;
	private String type;
	private String isExpDateRequired;

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIsExpDateRequired() {
		return isExpDateRequired;
	}

	public void setIsExpDateRequired(String isExpDateRequired) {
		this.isExpDateRequired = isExpDateRequired;
	}

}