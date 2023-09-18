package company.vk.polis.task1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DataUtils {
    private static final int MIN_MESSAGE_PER_USER = 10;
    private static final String[] names = new String[]{"Vasya", "Alina", "Petr", "Ira", "Ivan", "Tanya", "Anton"};
    private static final String[] texts = new String[]{"Have you listened to Morgenstern's new song yet?", "Hello!", "How are you?", "Bye", "Where are you?", "I'm fine", "Let's go somewhere", "I'm here"};

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
                State state = null;
                int randomIndex = random.nextInt(3);
                if (randomIndex == 0){
                    state = State.READ;
                } else if (randomIndex == 1) {
                    state = State.UNREAD;
                } else {
                    state = State.DELETED;
                }
                Long randomTime = random.nextLong(1000);
                Message message = new Message(k, text, i, randomTime, state);
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

    public static List<GroupChat> generateGroupChats(int maxChats, List<Integer> users, List<Integer> messages) {
        List<GroupChat> groupChats = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < maxChats; i++) {
            String avatarUrl = "group_avatar_" + i;

            List<Integer> userIds = new ArrayList<>();
            int numUsersInGroup = random.nextInt(users.size());
            for (int j = 0; j < numUsersInGroup; j++) {
                int userId = users.get(random.nextInt(users.size()));
                if (!userIds.contains(userId)) {
                    userIds.add(userId);
                }
            }

            List<Integer> messageIds = new ArrayList<>();
            int numMessagesInGroup = random.nextInt(messages.size());
            for (int j = 0; j < numMessagesInGroup; j++) {
                int messageId = messages.get(random.nextInt(messages.size()));
                messageIds.add(messageId);
            }

            GroupChat groupChat = new GroupChat(i, avatarUrl, userIds, messageIds);
            groupChats.add(groupChat);
        }

        return groupChats;
    }


    public static List<Entity> generateEntity() {
        int maxUserId = 5;
        List<User> users = generateUsers(maxUserId);
        Map<Integer, List<Message>> message = generateMessages(maxUserId);
        List<Chat> chats = generateChats(maxUserId, message);
        List<Integer> use = new ArrayList<>();
        List<Integer> mes = new ArrayList<>();
        List<Message> messages = message.entrySet().stream().flatMap(e -> e.getValue().stream()).toList();

        for (User user : users) {
            int userId = user.getId();
            use.add(userId);
        }

        for (Message value : messages) {
            int mesId = value.getId();
            mes.add(mesId);
        }

        List<GroupChat> groups= generateGroupChats(maxUserId, use, mes);

        List<Entity> combined = new ArrayList<>();
        combined.addAll(users);
        combined.addAll(chats);
        combined.addAll(messages);
        combined.addAll(groups);

        Random random = new Random();

        int garbage = random.nextInt(2);
        for (int i = 0; i < garbage; i++) {
            if (random.nextBoolean()) {
                combined.add(new User(null, names[random.nextInt(names.length - 1)], null));
            } else {
                combined.add(new User(-1, null, null));
            }
        }
        garbage = random.nextInt(2);
        for (int i = 0; i < garbage; i++) {
            switch (random.nextInt(4)) {
                case 0 -> combined.add(new Message(null, texts[random.nextInt(texts.length - 1)], -1, -1L, null));
                case 1 -> combined.add(new Message(-1, null, -1, -1L, State.READ));
                case 2 -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], null, -1L, null));
                default -> combined.add(new Message(-1, texts[random.nextInt(texts.length - 1)], -1, null, null));
            }
        }
        garbage = random.nextInt(2);
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
