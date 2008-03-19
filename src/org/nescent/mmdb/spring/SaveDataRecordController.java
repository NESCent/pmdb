package org.nescent.mmdb.spring;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.nescent.mmdb.hibernate.HibernateSessionFactory;
import org.nescent.mmdb.hibernate.dao.MmDataRecord;
import org.nescent.mmdb.hibernate.dao.MmDataRecordDAO;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudy;
import org.nescent.mmdb.hibernate.dao.MmExperimentStudyDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

public class SaveDataRecordController implements Controller {
    private static Logger log;

    private static Logger log() {
	if (log == null) {
	    log = Logger.getLogger(SaveDataRecordController.class);
	}
	return log;
    }

    @SuppressWarnings( { "unchecked", "unchecked" })
    public ModelAndView handleRequest(HttpServletRequest request,
	    HttpServletResponse response) {

	MmDataRecord newRecord = null;
	String id = request.getParameter("id");
	if (id == null) {
	    log().error("no id specified.");
	    throw new IllegalArgumentException("no id specified.");
	}
	MmExperimentStudyDAO mmStDao = new MmExperimentStudyDAO();
	MmExperimentStudy study = mmStDao.findById(Integer.valueOf(id));
	if (study == null) {
	    log().error("failed to retrieve the study with id: " + id);
	    throw new IllegalArgumentException(
		    "failed to retrieve the study with id: " + id);
	}

	MmDataRecordDAO rDao = new MmDataRecordDAO();

	Session sess = HibernateSessionFactory.getSession();
	Transaction tr = sess.beginTransaction();

	try {
	    for (Enumeration en = request.getParameterNames(); en
		    .hasMoreElements();) {
		String cvid = (String) en.nextElement();
		if (cvid == null || cvid.equals("id")
			|| cvid.indexOf("record_id") != -1)
		    continue;
		String value = request.getParameter(cvid);
		Double doubleValue = null;
		try {
		    doubleValue = Double.valueOf(value);
		} catch (Exception e) {

		}
		int intId = 0;
		try {
		    intId = Integer.valueOf(cvid).intValue();
		} catch (Exception e) {
		    log().error("invalid id: " + cvid);
		    throw new IllegalArgumentException("invalid id: " + cvid);
		}
		if (intId < 0) {
		    intId = -intId;
		    int record_id = intId / 10000;
		    String rid = "record_id_" + record_id;
		    String did = request.getParameter(rid);
		    if (did == null) // new record
		    {
			if (newRecord == null) {
			    newRecord = new MmDataRecord();
			    newRecord.setMmExperimentStudy(study);
			    study.getMmDataRecords().add(newRecord);
			}

			int fid = intId % 10000;

			if (fid == 1) // rname
			    newRecord.setName(value);
			else if (fid == 2) // type
			    newRecord.setType(value);
			else if (fid == 3) // outsd
			{
			    newRecord.setOutCrossingStdDev(doubleValue);
			} else if (fid == 4) // outv
			    newRecord.setOutCrossingValue(doubleValue);
			else if (fid == 5) // selfsd
			    newRecord.setSelfingStdDev(doubleValue);
			else if (fid == 6) // selfv
			    newRecord.setSelfingValue(doubleValue);
			else {
			    log().error(
				    "not supported field id in the Envieonmental Study: "
					    + fid);
			    throw new IllegalArgumentException(
				    "not supported field id in the Envieonmental Study: "
					    + fid);
			}

		    } else {
			MmDataRecord rcd = rDao.findById(Integer.valueOf(did));
			if (rcd == null) {
			    log().error(
				    "no Data Record found for the id: " + did);
			    throw new IllegalArgumentException(
				    "no Data Record found for the id: " + did);
			}
			int fid = intId % 10000;

			if (fid == 1) // rname
			    rcd.setName(value);
			else if (fid == 2) // type
			    rcd.setType(value);
			else if (fid == 3) // outsd
			    rcd.setOutCrossingStdDev(doubleValue);
			else if (fid == 4) // outv
			    rcd.setOutCrossingValue(doubleValue);
			else if (fid == 5) // selfsd
			    rcd.setSelfingStdDev(doubleValue);
			else if (fid == 6) // selfv
			    rcd.setSelfingValue(doubleValue);
			else {
			    log().error(
				    "not supported field id in the Envieonmental Study: "
					    + fid);
			    throw new IllegalArgumentException(
				    "not supported field id in the Envieonmental Study: "
					    + fid);
			}
			sess.update(rcd);
		    }
		} else {
		    log().error("invalid field id: " + intId);
		    throw new IllegalArgumentException("invalid field id: "
			    + intId);
		}
	    }

	    if (newRecord != null)
		sess.save(newRecord);
	    sess.flush();
	    tr.commit();

	    Map model = new HashMap();
	    model.put("envstudy", study);
	    model.put("tab", "staticstics");

	    return new ModelAndView("envstudy", model);
	} catch (HibernateException e) {
	    log().error("failed to sate the data record.", e);
	    throw e;
	} finally {
	    if (!tr.wasCommitted())
		tr.rollback();
	}

    }

}
