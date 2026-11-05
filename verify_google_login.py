"""
Verify which sites show a "Sign in with Google" option on their login screen.

Setup (run once):
    pip install playwright
    playwright install chromium

Run:
    python verify_google_login.py

Output:
    results.csv          -> site, status, evidence, checked_at (UTC)
    screenshots/<site>.png -> proof of what the login screen looked like

Status values:
    FOUND      Google sign-in element detected (check the screenshot to confirm)
    NOT_FOUND  Login screen loaded, no Google option detected
    NO_LOGIN   Could not open any login screen (needs manual check)
    ERROR      Page failed to load or site blocked the bot (needs manual check)

Only FOUND and NOT_FOUND with a matching screenshot should be trusted.
Always review the screenshot; sites with bot protection may hide the option.
"""
import csv
import re
import os
from datetime import datetime, timezone
from playwright.sync_api import sync_playwright

SITES = {
    "Uber": "https://www.uber.com",
    "Ola": "https://www.olacabs.com",
    "Rapido": "https://www.rapido.bike",
    "Zomato": "https://www.zomato.com",
    "Swiggy": "https://www.swiggy.com",
    "Zepto": "https://www.zeptonow.com",
    "BigBasket": "https://www.bigbasket.com",
    "Flipkart": "https://www.flipkart.com",
    "Myntra": "https://www.myntra.com",
    "Ajio": "https://www.ajio.com",
    "Nykaa": "https://www.nykaa.com",
    "Lenskart": "https://www.lenskart.com",
    "Tata 1mg": "https://www.1mg.com",
    "PharmEasy": "https://pharmeasy.in",
    "MakeMyTrip": "https://www.makemytrip.com",
    "Booking.com": "https://www.booking.com",
    "Agoda": "https://www.agoda.com",
    "Airbnb": "https://www.airbnb.com",
    "Spotify": "https://www.spotify.com",
    "Canva": "https://www.canva.com",
    "Grammarly": "https://www.grammarly.com",
    "Notion": "https://www.notion.so",
    "Duolingo": "https://www.duolingo.com",
    "Coursera": "https://www.coursera.org",
    "Udemy": "https://www.udemy.com",
    "LinkedIn": "https://www.linkedin.com",
    "Dropbox": "https://www.dropbox.com",
}

LOGIN_TEXT = re.compile(r"^\s*(log\s?in|sign\s?in|login/sign\s?up|sign\s?up)\s*$", re.I)
GOOGLE_TEXT = re.compile(r"(continue|sign\s?in|log\s?in|sign\s?up)\s+(in\s+)?with\s+google|^\s*google\s*$", re.I)
GOOGLE_URL_HINTS = ("accounts.google.com", "/auth/google", "google-oauth", "gsi/client", "gsi/button")


def open_login(page):
    """Try to reach a login screen. Returns True if something was clicked/opened."""
    for role in ("button", "link"):
        try:
            loc = page.get_by_role(role, name=LOGIN_TEXT).first
            if loc.is_visible(timeout=3000):
                loc.click(timeout=5000)
                page.wait_for_timeout(3000)
                return True
        except Exception:
            pass
    try:  # fallback: any visible element whose text looks like login
        loc = page.get_by_text(LOGIN_TEXT).first
        if loc.is_visible(timeout=2000):
            loc.click(timeout=5000)
            page.wait_for_timeout(3000)
            return True
    except Exception:
        pass
    return False


def find_google(page):
    """Return a short evidence string if a Google sign-in element exists, else ''."""
    # 1. visible text on page or any frame
    for frame in page.frames:
        try:
            el = frame.get_by_text(GOOGLE_TEXT).first
            if el.is_visible(timeout=1500):
                return f"text match: {el.inner_text()[:60]!r}"
        except Exception:
            pass
        try:
            el = frame.get_by_role("button", name=GOOGLE_TEXT).first
            if el.is_visible(timeout=1500):
                return "button with Google label"
        except Exception:
            pass
    # 2. Google Identity iframe / oauth links
    for frame in page.frames:
        if any(h in (frame.url or "") for h in GOOGLE_URL_HINTS):
            return f"google iframe: {frame.url[:80]}"
    for a in page.query_selector_all("a[href], iframe[src]"):
        ref = (a.get_attribute("href") or a.get_attribute("src") or "")
        if any(h in ref for h in GOOGLE_URL_HINTS):
            return f"google link: {ref[:80]}"
    return ""


def main():
    os.makedirs("screenshots", exist_ok=True)
    rows = []
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        for name, url in SITES.items():
            ctx = browser.new_context(viewport={"width": 1280, "height": 900},
                                      locale="en-IN")
            page = ctx.new_page()
            status, evidence = "ERROR", ""
            try:
                page.goto(url, wait_until="domcontentloaded", timeout=30000)
                page.wait_for_timeout(3000)
                if open_login(page):
                    evidence = find_google(page)
                    status = "FOUND" if evidence else "NOT_FOUND"
                else:
                    status, evidence = "NO_LOGIN", "could not locate a login button"
            except Exception as e:
                evidence = str(e)[:120]
            try:
                page.screenshot(path=f"screenshots/{name.replace(' ', '_')}.png")
            except Exception:
                pass
            rows.append([name, url, status, evidence,
                         datetime.now(timezone.utc).isoformat(timespec="seconds")])
            print(f"{name:12} {status:10} {evidence}")
            ctx.close()
        browser.close()

    with open("results.csv", "w", newline="", encoding="utf-8") as f:
        w = csv.writer(f)
        w.writerow(["site", "url", "status", "evidence", "checked_at_utc"])
        w.writerows(rows)
    print("\nSaved results.csv and screenshots/")


if __name__ == "__main__":
    main()