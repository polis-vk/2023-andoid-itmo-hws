package company.vk.polis.task1;

import java.util.*;

public class DataUtils {
    private static final int MIN_MESSAGE_PER_USER = 25;
    private static final String[] names = new String[]{"Vasya", "Alina", "Petr", "Ira", "Ivan", "Tanya", "Anton"};
    private static final String[] texts = new String[]{"Hello!", "How are you?", "Bye", "Where are you?", "I'm fine", "Let's go somewhere", "I'm here"};

    private static State createState(Integer id){//Может прийти нул
        List<String> states = List.of("READ","UNREAD","DELETED");
        Random random = new Random();
        String pickedOne = states.get(random.nextInt(states.size()));
        switch (pickedOne) {
            case "READ" -> {
                return new State.Read();
            }
            case "UNREAD" -> {
                return new State.Unread();
            }
            case "DELETED" -> {
                return new State.Deleted(id);
            }
            default -> {
                throw new IllegalArgumentException("Unknown state");
            }
        }
    }

    public static List<User> generateUsers(int maxId) {
        List<User> users = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < maxId; i++) {
            String name = names[random.nextInt(names.length)];
            User user = new User(i, name + i, random.nextBoolean() ? Integer.toString(i) : null);
            users.add(user);
        }
        return users;
    }

    public static Map<Integer, List<Message>> generateMessages(int maxUserId) {
        Map<Integer, List<Message>> map = new HashMap<>();
        Random random = new Random();
        int k = 0;
        for (int i = 0; i < maxUserId; i++, k++) {
            List<Message> messages = new ArrayList<>();
            int numMessages = random.nextInt(MIN_MESSAGE_PER_USER) + MIN_MESSAGE_PER_USER;
            for (int j = 0; j < numMessages; j++, k++) {
                String text = texts[random.nextInt(texts.length)];
                Message message = new Message(k, text, i, System.currentTimeMillis(), createState(i));
                messages.add(message);
            }
            map.put(i, messages);
        }
        return map;
    }

    public static List<Chat> generateChats(int maxUserId, Map<Integer, List<Message>> senders) {
        Random random = new Random();
        Map<UserPair, List<Integer>> userPairListMap = new HashMap<>();
        for (int i = 0; i < maxUserId; i++) {
            List<Message> messages = senders.get(i);
            for (Message message : messages) {
                int recieverId = random.nextInt(maxUserId);
                if (i == recieverId) {
                    recieverId = (recieverId + 1) % maxUserId;
                }
                UserPair userPair = new UserPair(recieverId, message.senderId());
                userPairListMap.putIfAbsent(userPair, new ArrayList<>());
                userPairListMap.get(userPair).add(message.id());

                userPair = new UserPair(message.senderId(), recieverId);
                userPairListMap.putIfAbsent(userPair, new ArrayList<>());
                userPairListMap.get(userPair).add(message.id());
            }
        }
        int k = 0;
        ArrayList<Chat> list = new ArrayList<>();
        for (Map.Entry<UserPair, List<Integer>> entry : userPairListMap.entrySet()) {
            list.add(new Chat(k, entry.getKey(), entry.getValue()));
            k++;
        }
        return list;
    }


    public static List<Entity> generateEntity() {
        int maxUserId = 10;
        List<User> users = generateUsers(maxUserId);
        Map<Integer, List<Message>> message = generateMessages(maxUserId);
        List<Chat> chats = generateChats(maxUserId, message);
        List<Message> messages = message.entrySet().stream().flatMap(e -> e.getValue().stream()).toList();

        List<Entity> combined = new ArrayList<>();
        combined.addAll(users);
        combined.addAll(chats);
        combined.addAll(messages);

        Random random = new Random();

        int garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            if (random.nextBoolean()) {
                combined.add(new User(null, names[random.nextInt(names.length - 1)], null));
            } else {
                combined.add(new User(-1, null, null));
            }
        }
        garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            switch (random.nextInt(4)) {
                case 0 -> combined.add(new Message(null, texts[random.nextInt(texts.length - 1)], -1, -1L, createState(-1)));
                case 1 -> combined.add(new Message(-1, null, -1, -1L, createState(-1)));
                case 2 -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], null, -1L, createState(null)));
                default -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], -1, null, createState(-1)));
            }
        }
        garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            switch (random.nextInt(4)) {
                case 0 -> combined.add(new Chat(null, null, null));
                case 1 -> combined.add(new Chat(-1, null, new ArrayList<>()));
                default -> combined.add(new Chat(-1, null, null));
            }
        }
        Collections.shuffle(combined);
        return combined;
    }
}
