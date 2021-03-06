
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Attachment;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	//Service under test----------------------------------------------------------	

	@Autowired
	private StoryService	storyService;

	// Supporting services ----------------------------------------------------

	@Autowired
	private TripService		tripService;
	@Autowired
	private ExplorerService	explorerService;

	// A++ saveAndFlush (permite realizar flush llamando al metodo flush de este objeto)
	@PersistenceContext
	private EntityManager	entityManager;


	@Test
	public void testCreatePositive() {
		super.authenticate("explorer1");
		Story story;
		story = this.storyService.create(this.tripService.findOne(super.getEntityId("trip1")));
		Assert.notNull(story);
		Assert.notNull(story.getExplorer());
		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("explorer1");
		Story story;
		final Attachment attachment1 = new Attachment();
		Trip trip1;

		final Collection<Attachment> attachments = new ArrayList<Attachment>();

		attachment1.setUrl("http://www.testStory.com");
		attachments.add(attachment1);
		trip1 = this.tripService.findOne(super.getEntityId("trip1"));
		story = this.storyService.create(trip1);

		story.setTitle("title story test");
		story.setText("text story test");
		story.setAttachments(attachments);
		story.setTrip(trip1);
		Assert.notNull(story.getExplorer());

		story = this.storyService.save(story);
		Assert.notNull(story.getId());
		//Hago flush en la bd
		this.entityManager.flush();
		Assert.isTrue(story.getExplorer().getStories().contains(story));

		super.unauthenticate();
	}
	@Test
	public void testFindAllPositive() {
		Collection<Story> storys;
		storys = this.storyService.findAll();
		Assert.notEmpty(storys);
	}

	@Test
	public void testFindOnePositive() {
		Story story;
		story = this.storyService.findOne(super.getEntityId("story1"));
		Assert.notNull(story);
	}

	@Test
	public void testDelete() {
		Story story;

		story = this.storyService.findOne(super.getEntityId("story2"));

		this.storyService.delete(story);
	}

	@Test
	public void testSpam() {
		Story story;
		Explorer explorer;
		boolean result;

		story = this.storyService.findOne(super.getEntityId("story1"));
		explorer = story.getExplorer();

		result = this.storyService.storyContainsSpam(explorer);

		Assert.isTrue(result);
	}

	@Test
	public void testUrlAttachments() {
		Collection<Attachment> result;

		result = this.storyService.urlAttachments(this.storyService.findOne(super.getEntityId("story1")));
		Assert.notNull(result);
	}

	@Test
	public void testStoryContainsSpam() {
		boolean res;

		res = this.storyService.storyContainsSpam(this.explorerService.findOne(super.getEntityId("explorer1")));
		Assert.isTrue(res);
	}

	@Test
	public void testFindAllStoriesByTripId() {
		Collection<Story> result;
		result = new ArrayList<Story>(this.storyService.findAllStoriesByTripId(super.getEntityId("trip1")));
		Assert.notNull(result);
	}

}
