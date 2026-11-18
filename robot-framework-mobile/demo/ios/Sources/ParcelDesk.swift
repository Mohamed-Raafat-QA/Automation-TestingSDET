import UIKit

@main
final class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    func application(_ application: UIApplication, didFinishLaunchingWithOptions options: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        let window = UIWindow(frame: UIScreen.main.bounds)
        window.rootViewController = DeskController()
        window.makeKeyAndVisible()
        self.window = window
        return true
    }
}

final class DeskController: UIViewController {
    private let stages = ["Booked", "In transit", "Delivered"]
    private let refs = ["SH-101", "SH-202"]
    private var progress = [0, 2]
    private var selected = 0
    private var quantity = 0
    private var discounted = false
    private var express = false
    private let panel = UIStackView()
    private let result = UILabel()

    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        panel.axis = .vertical
        panel.spacing = 6
        panel.translatesAutoresizingMaskIntoConstraints = false
        view.addSubview(panel)
        NSLayoutConstraint.activate([
            panel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: 12),
            panel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: 16),
            panel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -16)
        ])
        result.accessibilityIdentifier = "result"
        result.numberOfLines = 0
        render(checkout: false)
    }

    private func button(_ id: String, _ title: String, _ action: @escaping () -> Void) {
        let button = UIButton(type: .system)
        button.setTitle(title, for: .normal)
        button.accessibilityIdentifier = id
        button.addAction(UIAction { _ in action() }, for: .touchUpInside)
        panel.addArrangedSubview(button)
    }

    private func render(checkout: Bool) {
        for view in panel.arrangedSubviews { view.removeFromSuperview() }
        let title = UILabel()
        title.font = .preferredFont(forTextStyle: .title1)
        title.text = checkout ? "Notebook checkout" : "Track a shipment"
        panel.addArrangedSubview(title)
        button("tracking", "Shipments") { self.render(checkout: false) }
        button("checkout", "Checkout") { self.render(checkout: true) }
        panel.addArrangedSubview(result)
        if checkout {
            button("add", "Add notebook") { self.quantity += 1; self.basket() }
            button("remove", "Remove notebook") { self.quantity = max(0, self.quantity - 1); self.basket() }
            button("voucher", "Apply SAVE10") { self.discounted = true; self.basket() }
            button("express", "Express delivery") { self.express = true; self.basket() }
            button("order", "Place order") { self.placeOrder() }
            basket()
        } else {
            button("parcel_a", "SH-101 · Abu Dhabi") { self.select(0) }
            button("parcel_b", "SH-202 · Dubai") { self.select(1) }
            button("advance", "Advance status") { self.advance() }
            button("history", "Show history") {
                self.showShipment(self.stages.prefix(self.progress[self.selected] + 1).joined(separator: " > "))
            }
            select(selected)
        }
    }

    private func showShipment(_ status: String) { result.text = "\(refs[selected]) | \(status)" }
    private func select(_ index: Int) {
        selected = index
        showShipment(stages[progress[index]])
    }
    private func advance() {
        guard progress[selected] < stages.count - 1 else { showShipment("Already delivered"); return }
        progress[selected] += 1
        select(selected)
    }
    private var total: Int { quantity * (discounted ? 36 : 40) + (express && quantity > 0 ? 12 : 0) }
    private func basket() { result.text = "Items \(quantity) | Total AED \(total)" }
    private func placeOrder() {
        guard quantity > 0 else { result.text = "Add an item before ordering"; return }
        result.text = "Order confirmed | Paid AED \(total)"
        quantity = 0
        discounted = false
        express = false
    }
}
