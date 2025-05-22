import org.example.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(ResultAnalyzer.class)
public class MainTest {

    Task task1;
    Task task2;
    Task task3;
    TaskData taskData;
    Set<Task> taskSet1;
    Set<Task> taskSet2;
    Set<Task> taskSet3;

    @BeforeEach
    void setUp() {
        task1 = new Task("Java Collections", "Write List Interface",
                        "Ann", Priority.LOW, Status.IN_QUEUE);
        task2 = new Task("Java Collections", "Write Set Interface",
                        "Ann", Priority.MED, Status.ASSIGNED);
        task3 = new Task("Java Collections", "Write Map Interface",
                        "Bob", Priority.HIGH, Status.IN_QUEUE);

        taskSet1 = new HashSet<>();
        taskSet1.add(task1);
        taskSet2 = new HashSet<>();
        taskSet1.add(task2);
        taskSet3 = new HashSet<>();
        taskSet1.add(task3);

        taskData = new TaskData(taskSet1, taskSet2, taskSet3, new HashSet<>());
    }

    @DisplayName("Task sınıfı doğru access modifiers sahip mi")
    @Test
    public void testTaskAccessModifiers() throws NoSuchFieldException {
        Field assigneeFields = task1.getClass().getDeclaredField("assignee");
        Field descriptionsFields = task1.getClass().getDeclaredField("description");
        Field projectFields = task1.getClass().getDeclaredField("project");
        Field priorityFields = task1.getClass().getDeclaredField("priority");
        Field statusFields = task1.getClass().getDeclaredField("status");

        assertEquals(2, assigneeFields.getModifiers());
        assertEquals(2, descriptionsFields.getModifiers());
        assertEquals(2, projectFields.getModifiers());
        assertEquals(2, priorityFields.getModifiers());
        assertEquals(2, statusFields.getModifiers());
    }

    @DisplayName("Task sınıfı doğru typelara sahip mi")
    @Test
    public void testTaskTypes() {
        assertThat(task1.getAssignee(), instanceOf(String.class));
        assertThat(task1.getDescription(), instanceOf(String.class));
        assertThat(task1.getPriority(), instanceOf(Priority.class));
        assertThat(task1.getProject(), instanceOf(String.class));
        assertThat(task1.getStatus(), instanceOf(Status.class));
    }

    @DisplayName("TaskData sınıfı doğru access modifiers sahip mi")
    @Test
    public void testTaskDataAccessModifiers() throws NoSuchFieldException {
        Field annTasksField = taskData.getClass().getDeclaredField("annsTasks");
        Field bobTasksField = taskData.getClass().getDeclaredField("bobsTasks");
        Field carolTasksField = taskData.getClass().getDeclaredField("carolsTasks");
        Field unassignedTasksField = taskData.getClass().getDeclaredField("unassignedTasks");

        assertEquals(2, annTasksField.getModifiers());
        assertEquals(2, bobTasksField.getModifiers());
        assertEquals(2, carolTasksField.getModifiers());
        assertEquals(2, unassignedTasksField.getModifiers());
    }

    @DisplayName("TaskData getTasks method doğru çalışıyor mu ?")
    @Test
    public void testGetTasksMethod() {
        assertEquals(taskData.getTasks("ann"), taskSet1);
        assertEquals(taskData.getTasks("bob"), taskSet2);
        assertEquals(taskData.getTasks("carol"), taskSet3);
    }

    @DisplayName("TaskData getUnion method doğru çalışıyor mu ?")
    @Test
    public void testGetUnionMethod() {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet.add(task2);

        Set<Task> totals = taskData.getUnion(taskSet, taskSet2);
        assertEquals(2, totals.size());
    }

    @DisplayName("TaskData getIntersect() method doğru çalışıyor mu ?")
    @Test
    public void testGetIntersectMethod() throws NoSuchFieldException {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        taskSet.add(task2);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        Set<Task> intersections = taskData.getIntersection(taskSet, taskSet2);

        for(Task task: intersections){
            assertEquals(task, task2);
        }

        assertEquals(1, intersections.size());
    }

    @DisplayName("TaskData getDifference() method doğru çalışıyor mu ?")
    @Test
    public void testGetDifferenceMethod() throws NoSuchFieldException {
        Set<Task> taskSet = new HashSet<>();
        taskSet.add(task1);
        taskSet.add(task2);
        Set<Task> taskSet2 = new HashSet<>();
        taskSet2.add(task2);

        Set<Task> differences = taskData.getDifferences(taskSet, taskSet2);

        for(Task task: differences){
            assertEquals(task, task1);
        }

        assertEquals(1, differences.size());
    }

    @DisplayName("findUniqueWords doğru çalışıyor mu ?")
    @Test
    public void testFindUniqueWordsMethod() {
        assertEquals(143, StringSet.findUniqueWords().size());

        List<String> results = StringSet.findUniqueWords().stream().collect(Collectors.toList());
        assertEquals("a", results.get(0));
        assertEquals("wrote", results.get(results.size()-1));

    }
}
