package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("meetingService")
public class MeetingService {

	private Session session;

	public MeetingService() {
		session = DatabaseConnector.getInstance().getSession();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = this.session.createQuery(hql);
		return query.list();
	}

	public Meeting getById(long id) {
		return this.session.get(Meeting.class, id);
	}

	public void save(Meeting meeting) {
		this.session.beginTransaction();
		this.session.save(meeting);
		this.session.getTransaction().commit();
	}

	public boolean delete(long id) {
		Meeting meeting = getById(id);
		if (meeting == null) {
			return false;
		}
		this.session.beginTransaction();
		this.session.delete(meeting);
		this.session.getTransaction().commit();
		return true;
	}

	public Meeting update(Meeting meeting) {
		this.session.beginTransaction();
		this.session.update(meeting);
		this.session.getTransaction().commit();
		return meeting;
	}

}
