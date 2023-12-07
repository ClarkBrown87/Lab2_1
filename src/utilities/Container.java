package utilities;

import jade.core.Agent;

public final class Container {
    public static void Kill(Agent agent) {
        Runnable killContainer = () -> {
            try {
                System.out.println("Удаление текущего контейнера..");
                agent.getContainerController().kill();
            } catch (Exception e) {}
        };
        Thread t = new Thread(killContainer);
        t.start();
    }
}
