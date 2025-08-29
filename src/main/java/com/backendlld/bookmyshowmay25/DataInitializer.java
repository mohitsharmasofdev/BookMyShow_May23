package com.backendlld.bookmyshowmay25;

import com.backendlld.bookmyshowmay25.model.*;
import com.backendlld.bookmyshowmay25.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatTypeRepository seatTypeRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowSeatRepository showSeatRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Initializing Test Data ===");
        
        // Create test data
        createTestData();
        
        System.out.println("=== Test Data Initialization Complete ===");
    }

    private void createTestData() {
        // 1. Create Region
        Region region = new Region();
        region.setName("Mumbai");
        region = regionRepository.save(region);
        System.out.println("✓ Created Region: " + region.getName());

        // 2. Create Theatre
        Theatre theatre = new Theatre();
        theatre.setName("PVR Cinemas");
        theatre.setAddress("Andheri West, Mumbai");
        theatre.setRegion(region);
        theatre = theatreRepository.save(theatre);
        System.out.println("✓ Created Theatre: " + theatre.getName());

        // 3. Create Screen
        Screen screen = new Screen();
        screen.setName("Screen 1");
        screen.setScreenFeatures(Arrays.asList(Features.TWO_D, Features.THREE_D));
        screen = screenRepository.save(screen);
        System.out.println("✓ Created Screen: " + screen.getName());

        // 4. Create Seat Types
        SeatType premiumSeat = new SeatType();
        premiumSeat.setName("Premium");
        premiumSeat = seatTypeRepository.save(premiumSeat);

        SeatType regularSeat = new SeatType();
        regularSeat.setName("Regular");
        regularSeat = seatTypeRepository.save(regularSeat);
        System.out.println("✓ Created Seat Types: Premium, Regular");

        // 5. Create Seats
        List<Seat> seats = Arrays.asList(
            createSeat("A1", 1, 1, premiumSeat),
            createSeat("A2", 1, 2, premiumSeat),
            createSeat("A3", 1, 3, premiumSeat),
            createSeat("B1", 2, 1, regularSeat),
            createSeat("B2", 2, 2, regularSeat),
            createSeat("B3", 2, 3, regularSeat),
            createSeat("C1", 3, 1, regularSeat),
            createSeat("C2", 3, 2, regularSeat),
            createSeat("C3", 3, 3, regularSeat)
        );
        seats = seatRepository.saveAll(seats);
        System.out.println("✓ Created " + seats.size() + " seats");

        // 6. Create Artist
        Artist artist = new Artist();
        artist.setName("Amitabh Bachchan");
        artist = artistRepository.save(artist);
        System.out.println("✓ Created Artist: " + artist.getName());

        // 7. Create Movie
        Movie movie = new Movie();
        movie.setTitle("Sholay");
        movie.setDirector("Ramesh Sippy");
        movie.setYear("1975");
        movie.setGenre("Action");
        movie.setCast(Arrays.asList(artist));
        movie.setFeatures(Arrays.asList(Features.TWO_D));
        movie.setLanguages(Arrays.asList(Language.HINDI));
        movie = movieRepository.save(movie);
        System.out.println("✓ Created Movie: " + movie.getTitle());

        // 8. Create Show
        Show show = new Show();
        show.setMovie(movie);
        show.setScreen(screen);
        show.setTheatre(theatre);
        show.setTime(new Date(System.currentTimeMillis() + 86400000)); // Tomorrow
        show.setLanguage(Language.HINDI);
        show.setFeatures(Arrays.asList(Features.TWO_D));
        show = showRepository.save(show);
        System.out.println("✓ Created Show for movie: " + movie.getTitle());

        // 9. Create Show Seats
        List<ShowSeat> showSeats = Arrays.asList(
            createShowSeat(show, seats.get(0), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(1), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(2), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(3), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(4), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(5), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(6), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(7), ShowSeatStatus.AVAILABLE),
            createShowSeat(show, seats.get(8), ShowSeatStatus.AVAILABLE)
        );
        showSeats = showSeatRepository.saveAll(showSeats);
        System.out.println("✓ Created " + showSeats.size() + " show seats");

        // 10. Create Users
        User user1 = new User();
        user1.setUsername("testuser1");
        user1.setEmail("test1@example.com");
        user1 = userRepository.save(user1);
        System.out.println("✓ Created User 1: " + user1.getUsername());
        
        User user2 = new User();
        user2.setUsername("testuser2");
        user2.setEmail("test2@example.com");
        user2 = userRepository.save(user2);
        System.out.println("✓ Created User 2: " + user2.getUsername());

        System.out.println("\n=== Test Data Summary ===");
        System.out.println("User 1 ID: " + user1.getId());
        System.out.println("User 2 ID: " + user2.getId());
        System.out.println("Show ID: " + show.getId());
        System.out.println("Available Seat IDs: " + showSeats.stream().map(ShowSeat::getId).toList());
        System.out.println("You can now test booking with:");
        System.out.println("- User IDs: " + user1.getId() + ", " + user2.getId());
        System.out.println("- Show ID: " + show.getId());
        System.out.println("- Seat IDs: " + showSeats.stream().map(ShowSeat::getId).toList());
    }

    private Seat createSeat(String name, int row, int column, SeatType seatType) {
        Seat seat = new Seat();
        seat.setName(name);
        seat.setSeatRow(row);
        seat.setSeatColumn(column);
        seat.setSeatType(seatType);
        return seat;
    }

    private ShowSeat createShowSeat(Show show, Seat seat, ShowSeatStatus status) {
        ShowSeat showSeat = new ShowSeat();
        showSeat.setShow(show);
        showSeat.setSeat(seat);
        showSeat.setStatus(status);
        return showSeat;
    }
} 