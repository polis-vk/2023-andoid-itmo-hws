package company.vk.polis.task1;

import java.util.*;
import java.util.stream.IntStream;

public class DataUtils {
    private static final int MIN_MESSAGE_PER_USER = 25;
    private static final int MESSAGE_STATES_COUNT = 3;
    private static final String[] names = new String[]{"Vasya", "Alina", "Petr", "Ira", "Ivan", "Tanya", "Anton"};
    private static final String[] texts = new String[]{"Hello!", "How are you?", "Bye", "Where are you?", "I'm fine", "Let's go somewhere", "I'm here"};
    private static int messageId = 0;


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
        for (int i = 0; i < maxUserId; i++) {
            List<Message> messages = new ArrayList<>();
            int numMessages = random.nextInt(MIN_MESSAGE_PER_USER) + MIN_MESSAGE_PER_USER;
            for (int j = 0; j < numMessages; j++) {
                String text = texts[random.nextInt(texts.length)];
                MessageState state;
                switch (random.nextInt(MESSAGE_STATES_COUNT)) {
                    case 0 -> state = new MessageState(MessageStateEnum.READ);
                    case 1 -> state = new MessageState(MessageStateEnum.UNREAD);
                    default -> {
                        state = new MessageState(MessageStateEnum.DELETED, i);
                    }
                }
                Message message = new Message(messageId++, text, i, System.currentTimeMillis(), state);
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

    public static List<GroupChat> generateGroupChats(int maxUserId, int toGenerate, List<Message> allMessages) {
        Random random = new Random();
        List<GroupChat> groupChats = new ArrayList<>();
        int groupChatId = 0;
        for (int i = 0; i < toGenerate; i++) {
            Map<Integer, List<Message>> senders = generateMessages(maxUserId);
            allMessages.addAll(senders.entrySet().stream().flatMap(e -> e.getValue().stream()).toList());
            List<Integer> users = new ArrayList<>(IntStream.range(0, maxUserId).boxed().toList());
            Collections.shuffle(users, random);
            int usersCnt = random.nextInt(1, maxUserId + 1);
            users = users.subList(0, usersCnt);
            List<Integer> messages = new ArrayList<>();
            for (int user : users) {
                messages.addAll(senders.get(user).stream().map(Message::id).toList());
            }
            groupChats.add(new GroupChat(groupChatId++, "test-link", users, messages));
        }
        return groupChats;
    }


    public static List<Entity> generateEntity() {
        int maxUserId = 10;
        int groupChatsToGenerate = 5;
        List<User> users = generateUsers(maxUserId);
        Map<Integer, List<Message>> message = generateMessages(maxUserId);
        List<Chat> chats = generateChats(maxUserId, message);
        List<Message> messages = new ArrayList<>(message.entrySet().stream().flatMap(e -> e.getValue().stream()).toList());
        List<GroupChat> groupChats = generateGroupChats(maxUserId, groupChatsToGenerate, messages);

        List<Entity> combined = new ArrayList<>();
        combined.addAll(users);
        combined.addAll(chats);
        combined.addAll(messages);
        combined.addAll(groupChats);

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
                case 0 -> combined.add(new Message(null, texts[random.nextInt(texts.length - 1)], -1, -1L, null));
                case 1 -> combined.add(new Message(-1, null, -1, -1L, null));
                case 2 -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], null, -1L, null));
                default -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], -1, null, null));
            }
        }
        garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            switch (random.nextInt(3)) {
                case 0 -> combined.add(new Chat(null, null, null));
                case 1 -> combined.add(new Chat(-1, null, new ArrayList<>()));
                default -> combined.add(new Chat(-1, null, null));
            }
        }
        garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            switch (random.nextInt(2)) {
                case 0 -> combined.add(new GroupChat(-1, "test-link", new ArrayList<>(), new ArrayList<>()));
                default -> combined.add(new GroupChat(-1, null, new ArrayList<>(), new ArrayList<>()));
            }
        }
        Collections.shuffle(combined);
        return combined;
    }
}
