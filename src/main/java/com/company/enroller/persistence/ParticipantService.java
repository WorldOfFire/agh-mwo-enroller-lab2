package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll(String sortBy, String sortOrder, String key) {
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        List<Participant> participants = query.list();

        if (key != null && !key.trim().isEmpty()) {
            String filterKey = key.trim().toLowerCase();
            participants = participants.stream()
                    .filter(p -> p.getLogin().toLowerCase().contains(filterKey))
                    .collect(Collectors.toList());
        }

        if ("login".equalsIgnoreCase(sortBy)) {
            Comparator<Participant> comparator = Comparator.comparing(
                    Participant::getLogin, String.CASE_INSENSITIVE_ORDER
            );

            if ("DESC".equalsIgnoreCase(sortOrder)) {
                comparator = comparator.reversed();
            }

            participants.sort(comparator);
        }
        return participants;
    }

    public Participant findByLogin(String login) {
        return connector.getSession().get(Participant.class, login);
    }

    public Participant add(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        transaction.commit();
        return participant;
    }

    public void update(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().merge(participant);
        transaction.commit();
    }

    public void delete(Participant participant) {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().delete(participant);
        transaction.commit();
    }

}
