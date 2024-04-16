package ex2

import org.junit.*
import org.junit.Assert.*

class TestConferenceReviewing:
  private val cr = ConferenceReviewing()

  private def init(): Unit =
    cr.loadReview(1, 8, 8, 6, 8)
    cr.loadReview(1, 9, 9, 6, 9)
    cr.loadReview(2, 9, 9, 10, 9)
    cr.loadReview(2, 4, 6, 10, 6)
    cr.loadReview(3, 3, 3, 3, 3)
    cr.loadReview(3, 4, 4, 4, 4)
    cr.loadReview(4, 6, 6, 6, 6)
    cr.loadReview(4, 7, 7, 8, 7)
    val map = Map.apply((Question.RELEVANCE, 8), (Question.SIGNIFICANCE, 8), (Question.CONFIDENCE, 7), (Question.FINAL, 8))
    cr.loadReview(4, map)
    cr.loadReview(5, 6, 6, 6, 10)
    cr.loadReview(5, 7, 7, 7, 10)

  @Test def orderedScores(): Unit =
    init()
    assertEquals(cr.orderedScores(2, Question.RELEVANCE), List.apply(4, 9))
    assertEquals(cr.orderedScores(4, Question.CONFIDENCE), List.apply(6, 7, 8))
    assertEquals(cr.orderedScores(5, Question.FINAL), List.apply(10, 10))

  @Test def testAverageFinalScore(): Unit =
    init()
    assertEquals(cr.averageFinalScore(1), 8.5, 0.01);
    assertEquals(cr.averageFinalScore(2), 7.5, 0.01);
    assertEquals(cr.averageFinalScore(3), 3.5, 0.01);
    assertEquals(cr.averageFinalScore(4), 7.0, 0.01);
    assertEquals(cr.averageFinalScore(5), 10.0, 0.01);

  @Test def testAcceptedArticles(): Unit =
    init()
    assertEquals(cr.acceptedArticles(), Set.apply(1, 2, 4))

  @Test def testSortedAcceptedArticles(): Unit =
    init()
    assertEquals(
      cr.sortedAcceptedArticles(),
      List.apply((4, 7.0), (2, 7.5), (1, 8.5))
    )

  @Test def optionalTestAverageWeightedFinalScore(): Unit =
    init()
    assertEquals(cr.averageWeightedFinalScoreMap().get(1), (4.8 + 5.4) / 2)
    assertEquals(cr.averageWeightedFinalScoreMap().get(2), (9.0 + 6.0) / 2)
    assertEquals(cr.averageWeightedFinalScoreMap().get(3), (0.9 + 1.6) / 2)
    assertEquals(cr.averageWeightedFinalScoreMap().get(4), (3.6 + 5.6 + 5.6) / 3)
    assertEquals(cr.averageWeightedFinalScoreMap().get(5), (6.0 + 7.0) / 2)
    assertEquals(cr.averageWeightedFinalScoreMap().size, 5)
