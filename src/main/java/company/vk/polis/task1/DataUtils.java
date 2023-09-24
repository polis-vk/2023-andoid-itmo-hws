package company.vk.polis.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class DataUtils {
    private static final int MIN_MESSAGE_PER_USER = 25;
    private static final String[] names = new String[]{"Vasya", "Alina", "Petr", "Ira", "Ivan", "Tanya", "Anton"};
    private static final String[] texts = new String[]{"Hello!", "How are you?", "Bye", "Where are you?", "I'm fine", "Let's go somewhere", "I'm here"};

    public static List<User> generateUsers(int maxId) {
        List<User> users = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < maxId; i++) {
            String name = names[random.nextInt(names.length)];
            User user = new User(i, name + i, random.nextBoolean() ? Integer.toString(i) : null, new ArrayList<>());
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
                MessageState ms;
                switch (random.nextInt(3))
                {
                    case 0 -> ms = MessageState.Read.INSTANCE;
                    case 1 -> ms = MessageState.Unread.INSTANCE;
                    default -> ms = new MessageState.Deleted(random.nextBoolean() ? k : i);
                }
                Message message = new Message(k, text, i, System.currentTimeMillis(), ms);
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

    public static List<GroupChat> generateGroupChats(int maxUserId, Map<Integer, List<Message>> senders) {
        Random random = new Random();
        Map<List<Integer>, List<Integer>> userGroupUsersListMap = new HashMap<>();
        for (int i = 0; i < maxUserId; i++) {
            List<Message> messages = senders.get(i);
            for (Message message : messages) {
                int nGroupUsers = random.nextInt(maxUserId);
                List<Integer> groupUsers = new ArrayList<Integer>();
                for(int j = 0; j < nGroupUsers; j++)
                {
                    groupUsers.add(random.nextInt(maxUserId));
                }
                userGroupUsersListMap.putIfAbsent(groupUsers, new ArrayList<>());
                userGroupUsersListMap.get(groupUsers).add(message.id());
            }
        }
        int k = 0;
        ArrayList<GroupChat> list = new ArrayList<>();
        for (Map.Entry<List<Integer>, List<Integer>> entry : userGroupUsersListMap.entrySet()) {
            list.add(new GroupChat(k, random.nextBoolean() ? Integer.toString(k) : null, entry.getKey(), entry.getValue()));
            k++;
        }
        return list;
    }

    public static List<User> addUsersToGroups(List<User> users, List<GroupChat> groupChats)
    {
        Map<Integer, User> mapUsers = users.stream().collect(Collectors.toMap(User::getId, user -> user));

        for (GroupChat groupChat : groupChats)
        {
            for (Integer groupUserId : groupChat.getUserIds())
            {
                mapUsers.get(groupUserId).addToGroupChat(groupChat.getId());
            }
        }

        return new ArrayList<>(mapUsers.values());
    }

    public static List<Entity> generateEntity() {
        int maxUserId = 10;
        List<User> users = generateUsers(maxUserId);
        Map<Integer, List<Message>> privateMessage = generateMessages(maxUserId);
        List<Chat> chats = generateChats(maxUserId, privateMessage);
        Map<Integer, List<Message>> groupMessage = generateMessages(maxUserId);
        List<GroupChat> groupChats = generateGroupChats(maxUserId, groupMessage);

        users = addUsersToGroups(users, groupChats);

        List<Message> privateMessages = privateMessage.entrySet().stream().flatMap(e -> e.getValue().stream()).toList();
        List<Message> groupMessages = groupMessage.entrySet().stream().flatMap(e -> e.getValue().stream()).toList();

        List<Entity> combined = new ArrayList<>();
        combined.addAll(users);
        combined.addAll(chats);
        combined.addAll(groupChats);
        combined.addAll(privateMessages);
        combined.addAll(groupMessages);

        Random random = new Random();

        int garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            if (random.nextBoolean()) {
                combined.add(new User(null, names[random.nextInt(names.length - 1)], null, new ArrayList<>()));
            } else {
                combined.add(new User(-1, null, null, new ArrayList<>()));
            }
        }
        garbage = random.nextInt(50);
        for (int i = 0; i < garbage; i++) {
            switch (random.nextInt(5)) {
                case 0 -> combined.add(new Message(null, texts[random.nextInt(texts.length - 1)], -1, -1L, MessageState.Unread.INSTANCE));
                case 1 -> combined.add(new Message(-1, null, -1, -1L, MessageState.Unread.INSTANCE));
                case 2 -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], null, -1L, MessageState.Unread.INSTANCE));
                case 3 -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], -1, null, MessageState.Unread.INSTANCE));
                default -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], -1, -1L, null));
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
