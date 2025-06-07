@RestController
@RequestMapping("/api/progress")
public class ProgressController {

    @Autowired
    private ModuleProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @PostMapping("/complete")
    public ResponseEntity<?> markModuleComplete(@RequestParam Long userId, @RequestParam Long moduleId) {
        User user = userRepository.findById(userId).orElseThrow();
        Module module = moduleRepository.findById(moduleId).orElseThrow();

        ModuleProgress progress = progressRepository
            .findByUserAndModule(user, module)
            .orElse(new ModuleProgress());

        progress.setUser(user);
        progress.setModule(module);
        progress.setCompleted(true);
        progressRepository.save(progress);

        return ResponseEntity.ok("Module marked as complete");
    }

    @GetMapping("/user/{userId}")
    public List<ModuleProgress> getUserProgress(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return progressRepository.findByUser(user);
    }
}
