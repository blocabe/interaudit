package com.interaudit.domain.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name = "DOCUMENTS",schema="interaudit")
public class Document implements java.io.Serializable{
	
	
	private static Map<String, DocumentLocker> documentLockers = new HashMap<String, DocumentLocker>();
	private static Object fileSystemLocker = new Object();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	/*
	@GeneratedValue(generator="DocumentSeq")
	@SequenceGenerator(name="DocumentSeq",sequenceName="DOCUMENT_SEQ", allocationSize=1)
	*/
	private Long id;
	
	@Column(name="FILE_NAME")
    private String fileName;
    
    @Transient
    private File file;
    
    @Column(name = "DESCRIPTION", nullable = true)
    private String description;
    
    @OneToOne
	@JoinColumn(name = "OWNER")
    private Employee createdUser;   
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;   
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    
    @Column(name="TITLE")
    private String title;
    
    
    
    @Column(name = "VERSION", nullable = false)
	@Version
	private int version;
    
    
    public Document(String fileName, String description, Employee createdUser,
			Date createDate, String title) {
		super();
		this.fileName = fileName;
		this.description = description;
		this.createdUser = createdUser;
		this.createDate = createDate;
		this.title = title;
	}
    
    
    

    
    public Document() {}
    
    public Document(File f) {
        this.file = f;
        if (this.file != null) {
            this.fileName = f.getName();
        }
    }
    
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Employee getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(Employee createdUser) {
		this.createdUser = createdUser;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
    
	
	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Document) ? (this.id != null && this.id.equals(((Document)obj).getId())) : false;
    }
    
    public Object getFileLocker() {

        synchronized (fileSystemLocker) {
            DocumentLocker documentLocker = documentLockers.get(this.getFile().getAbsolutePath());
            if (documentLocker == null) {
                documentLocker = new DocumentLocker(Thread.currentThread()
                        .getId());
                documentLockers.put(this.getFile().getAbsolutePath(),
                        documentLocker);
            } else {
                Long threadId = Thread.currentThread().getId();
                if (documentLocker.containsThreadId(threadId) == false) {
                    documentLocker.registerThread(threadId);
                }
            }
            return documentLocker;
        }
    }

    public void removeFileLocker() {
        synchronized (fileSystemLocker) {
            String filePath = this.getFile().getAbsolutePath();
            DocumentLocker documentLocker = documentLockers.get(filePath);
            if (documentLocker != null) {
                documentLocker.removeThreadId(Thread.currentThread().getId());
                if (documentLocker.hasMoreThreads() == false) {
                    documentLockers.remove(filePath);
                }
            }

        }
    }
    
	private static class DocumentLocker {
        
        private List<Long> threadIds = new ArrayList<Long>();

        public DocumentLocker(Long threadId) {
            threadIds.add(threadId);
        }

        public boolean containsThreadId(Long threadId) {
            return threadIds.contains(threadId);
        }

        public boolean removeThreadId(Long threadId) {
            return threadIds.remove(threadId);
        }

        public List<Long> getThreadIds() {
            return threadIds;
        }

        public void setThreadIds(List<Long> threadIds) {
            this.threadIds = threadIds;
        }

        public boolean registerThread(Long threadId) {
            return threadIds.add(threadId);
        }

        public boolean hasMoreThreads() {
            return threadIds.size() == 0;
        }
    }

	
    
}
