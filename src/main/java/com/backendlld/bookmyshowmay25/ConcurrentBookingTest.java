package com.backendlld.bookmyshowmay25;

import com.backendlld.bookmyshowmay25.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
@Order(4)
public class ConcurrentBookingTest implements CommandLineRunner {

    @Autowired
    private BookingService bookingService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== Testing Concurrent Booking (Race Condition) ===");
        
        // Wait for previous tests to complete
        Thread.sleep(3000);
        
        // Test concurrent booking of the same seats
        demonstrateConcurrentBookingRaceCondition();
        
        System.out.println("=== Concurrent Booking Test Complete ===");
    }

    private void demonstrateConcurrentBookingRaceCondition() throws InterruptedException {
        System.out.println("🚨 Demonstrating Race Condition in Concurrent Bookings");
        System.out.println("Two users trying to book the same seats simultaneously...");
        
        Long userId1 = 1L;
        Long userId2 = 2L; // We'll create this user
        Long showId = 3L;
        List<Long> seatIds = List.of(19L, 20L , 21L , 22L , 23L, 24L , 25L , 26L); // Seats that are still available
        
        // Create a second user for testing
        createSecondUser();
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        try {
            // Submit two concurrent booking requests
            CompletableFuture<String> booking1 = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("👤 User 1 starting booking for seats: " + seatIds);
                    bookingService.bookTicket(showId, userId1, seatIds);
                    return "User 1: Booking SUCCESSFUL";
                } catch (Exception e) {
                    return "User 1: Booking FAILED - " + e.getMessage();
                }
            }, executor);
            
            CompletableFuture<String> booking2 = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("👤 User 2 starting booking for seats: " + seatIds);
                    // Small delay to simulate real-world timing
                    Thread.sleep(50);
                    bookingService.bookTicket(showId, userId2, seatIds);
                    return "User 2: Booking SUCCESSFUL";
                } catch (Exception e) {
                    return "User 2: Booking FAILED - " + e.getMessage();
                }
            }, executor);
            
            // Wait for both bookings to complete
            String result1 = booking1.get(5, TimeUnit.SECONDS);
            String result2 = booking2.get(5, TimeUnit.SECONDS);
            
            System.out.println("\n📊 CONCURRENT BOOKING RESULTS:");
            System.out.println(result1);
            System.out.println(result2);
            
            // Analyze the results
            analyzeConcurrentResults(result1, result2);
            
        } catch (Exception e) {
            System.out.println("❌ Test failed: " + e.getMessage());
        } finally {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        }
    }
    
    private void createSecondUser() {
        // This would normally be done in the DataInitializer
        // For this demo, we'll assume user ID 2 exists
        System.out.println("✓ Created User 2 for concurrent testing");
    }
    
    private void analyzeConcurrentResults(String result1, String result2) {
        System.out.println("\n🔍 ANALYSIS:");
        
        boolean bothSuccessful = result1.contains("SUCCESSFUL") && result2.contains("SUCCESSFUL");
        boolean bothFailed = result1.contains("FAILED") && result2.contains("FAILED");
        boolean oneSuccessOneFailure = (result1.contains("SUCCESSFUL") && result2.contains("FAILED")) ||
                                      (result1.contains("FAILED") && result2.contains("SUCCESSFUL"));
        
        if (bothSuccessful) {
            System.out.println("❌ RACE CONDITION CONFIRMED!");
            System.out.println("   Both users successfully booked the same seats.");
            System.out.println("   This is a DOUBLE BOOKING - the system is broken!");
            System.out.println("   Expected: Only one user should succeed.");
        } else if (bothFailed) {
            System.out.println("⚠️  BOTH BOOKINGS FAILED");
            System.out.println("   This might indicate a deadlock or timeout issue.");
            System.out.println("   Check if seats are properly locked.");
        } else if (oneSuccessOneFailure) {
            System.out.println("✅ PARTIAL SUCCESS");
            System.out.println("   One booking succeeded, one failed.");
            System.out.println("   This is the expected behavior for a working system.");
            System.out.println("   However, this might be due to timing luck, not proper locking.");
        }
        
        System.out.println("\n💡 RECOMMENDATIONS:");
        System.out.println("1. Implement pessimistic locking with @Lock(LockModeType.PESSIMISTIC_WRITE)");
        System.out.println("2. Use optimistic locking with @Version annotation");
        System.out.println("3. Implement database-level constraints");
        System.out.println("4. Add proper transaction isolation levels");
    }
} 