package utilities;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;

//позволяет агентам регистрировать и искать другие агенты, предоставляющие определенные сервисы

public final class DFUtilities {
    // ищем всех агентов с заданным типом
    public static AID[] searchService(Agent agent, String serviceName) {
        DFAgentDescription desc = new DFAgentDescription(); // объект при помощи которого можно описать агента
        ServiceDescription service_desc = new ServiceDescription(); // объекта для описания сервиса агента
        service_desc.setType(serviceName); // устанавливаем тип сервиса
        desc.addServices(service_desc); // добавляем сервис в агента

        SearchConstraints all = new SearchConstraints(); // создаем объект ограничений поиска
        all.setMaxResults((long) -1); // задаем параметр - искать все
        try {
            DFAgentDescription[] result = DFService.search(agent, desc, all); // искать агентов соответсвущих
            AID[] agents = new AID[result.length];
            for (int i = 0; i < result.length; i++)
                agents[i] = result[i].getName();
            return agents;
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return new AID[0];
    }

    public static void register(Agent agent, ArrayList<String> services) {
        DFAgentDescription desc = new DFAgentDescription();
        desc.setName(agent.getAID()); // устанавливаем имя агента из передаваемого

        for (String service : services) {
            ServiceDescription service_desc = new ServiceDescription();
            service_desc.setName(agent.getLocalName());
            service_desc.setType(service);
            desc.addServices(service_desc);
        }
        try {
            DFService.register(agent, desc);
        } catch (Exception e) {}
    }

    public static void register(Agent agent, String serviceName) {
        var list = new ArrayList<String>();
        list.add(serviceName);
        register(agent, list);
    }

    public static void deregister(Agent agent) {
        try {
            DFService.deregister(agent);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public static void addService(Agent agent, String service_type) {
        DFAgentDescription desc = new DFAgentDescription();
        desc.setName(agent.getAID());
        try {
            DFAgentDescription[] agents = DFService.search(agent, desc);
            ServiceDescription service_desc = new ServiceDescription();
            service_desc.setType(service_type);
            service_desc.setName(agent.getLocalName());
            agents[0].addServices(service_desc);
            DFService.modify(agent, agents[0]);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
