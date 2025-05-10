package com.company.enroller.persistence;

import com.company.enroller.model.Meeting;
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

	public Meeting update(long id, Meeting meeting) {
		Meeting existingMeeting = getById(id);
		if (existingMeeting == null) {
			return null;
		}
		existingMeeting.setTitle(meeting.getTitle());
		existingMeeting.setDescription(meeting.getDescription());
		existingMeeting.setDate(meeting.getDate());
		this.session.beginTransaction();
		this.session.update(existingMeeting);
		this.session.getTransaction().commit();
		return existingMeeting;
	}
}
