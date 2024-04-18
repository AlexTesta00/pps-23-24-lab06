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
    val map = Map.apply(
      (Question.RELEVANCE, 8),
      (Question.SIGNIFICANCE, 8),
      (Question.CONFIDENCE, 7),
      (Question.FINAL, 8)
    )
    cr.loadReview(4, map)
    cr.loadReview(5, 6, 6, 6, 10)
    cr.loadReview(5, 7, 7, 7, 10)

  @Test def orderedScores(): Unit =
    init()
    assertEquals(List.apply(4, 9), cr.orderedScores(2, Question.RELEVANCE))
    assertEquals(List.apply(6, 7, 8), cr.orderedScores(4, Question.CONFIDENCE))
    assertEquals(List.apply(10, 10), cr.orderedScores(5, Question.FINAL))

  @Test def testAverageFinalScore(): Unit =
    init()
    assertEquals(8.5, cr.averageFinalScore(1), 0.01);
    assertEquals(7.5, cr.averageFinalScore(2), 0.01);
    assertEquals(3.5, cr.averageFinalScore(3), 0.01);
    assertEquals(7.0, cr.averageFinalScore(4), 0.01);
    assertEquals(10.0, cr.averageFinalScore(5), 0.01);

  @Test def testAcceptedArticles(): Unit =
    init()
    assertEquals(Set.apply(1, 2, 4), cr.acceptedArticles())

  @Test def testSortedAcceptedArticles(): Unit =
    init()
    assertEquals(
      List.apply((4, 7.0), (2, 7.5), (1, 8.5)),
      cr.sortedAcceptedArticles()
    )

  @Test def optionalTestAverageWeightedFinalScore(): Unit =
    init()
    assertEquals((4.8 + 5.4) / 2, cr.averageWeightedFinalScoreMap().get(1))
    assertEquals((9.0 + 6.0) / 2, cr.averageWeightedFinalScoreMap().get(2))
    assertEquals((0.9 + 1.6) / 2, cr.averageWeightedFinalScoreMap().get(3))
    assertEquals((3.6 + 5.6 + 5.6) / 3, cr.averageWeightedFinalScoreMap().get(4))
    assertEquals((6.0 + 7.0) / 2, cr.averageWeightedFinalScoreMap().get(5))
    assertEquals(5, cr.averageWeightedFinalScoreMap().size)
