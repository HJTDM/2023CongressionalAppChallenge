package com.example.myfinance.data

import com.example.myfinance.R

/**
 * Object (like a singleton) that contains data (images and titles)
 * for the tool and lesson cards
 */
object DataSource {

    val tools: List<Tool> = listOf(
        Tool(
            R.drawable.tool_budget_planner,
            "Budget Planner"
        ),
        Tool(
            R.drawable.tool_tax_calculator,
            "Tax Calculator"
        ),
        Tool(
            R.drawable.tool_interest_calculator,
            "Interest Calculator"
        ),
        Tool(
            R.drawable.tool_stock_market,
            "Stock Market"
        )
    )

    val unit1Lessons: List<Lesson> = listOf(
        Lesson(
            R.drawable.unit_1_lesson_1,
            "Lesson 1: What is Finance?"
        ),
        Lesson(
            R.drawable.unit_1_lesson_2,
            "Lesson 2: Why is Financial Literacy Important?"
        )
    )

    val unit2Lessons: List<Lesson> = listOf(
        Lesson(
            R.drawable.unit_2_lesson_1,
            "Lesson 1: What is Economics?"
        ),
        Lesson(
            R.drawable.unit_2_lesson_2,
            "Lesson 2: Resources"
        ),
        Lesson(
            R.drawable.unit_2_lesson_3,
            "Lesson 3: Opportunity Cost and Economic Growth"
        ),
        Lesson(
            R.drawable.unit_2_lesson_4,
            "Lesson 4: Trade"
        ),
        Lesson(
            R.drawable.unit_2_lesson_5,
            "Lesson 5: Introduction to Supply and Demand"
        ),
        Lesson(
            R.drawable.unit_2_lesson_6,
            "Lesson 6: Demand"
        ),
        Lesson(
            R.drawable.unit_2_lesson_7,
            "Lesson 7: Supply"
        ),
        Lesson(
            R.drawable.unit_2_lesson_8,
            "Lesson 8: Market Equilibrium"
        ),
        Lesson(
            R.drawable.unit_2_lesson_9,
            "Lesson 9: Gross Domestic Product"
        ),
        Lesson(
            R.drawable.unit_2_lesson_10,
            "Lesson 10: Inflation"
        ),
        Lesson(
            R.drawable.unit_2_lesson_11,
            "Lesson 11: Unemployment"
        ),
        Lesson(
            R.drawable.unit_2_lesson_12,
            "Lesson 12: The Business Cycle"
        )
    )

    val unit3Lessons: List<Lesson> = listOf(
        Lesson(
            R.drawable.unit_3_lesson_1,
            "Lesson 1: What are Banks?"
        ),
        Lesson(
            R.drawable.unit_3_lesson_2,
            "Lesson 2: Types of Bank Accounts"
        ),
        Lesson(
            R.drawable.unit_3_lesson_3,
            "Lesson 3: Credit"
        ),
        Lesson(
            R.drawable.unit_3_lesson_4,
            "Lesson 4: Loans"
        ),
        Lesson(
            R.drawable.unit_3_lesson_5,
            "Lesson 5: Interest"
        )
    )

    val unit4Lessons: List<Lesson> = listOf(
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 1: What are Taxes?"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 2: Consumption Tax"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 3: Income Tax"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 4: Payroll Tax"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 5: Property Tax"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 6: Capital Gains Tax"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 7: How To Do Taxes"
        )
    )

    val unit5Lessons: List<Lesson> = listOf(
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 1: What is Investing?"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 2: The Stock Market"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 3: Stocks"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 4: Bonds"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 5: Mutual Funds"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 6: Exchange-Traded Funds"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 7: Trading"
        )
    )

    val unit6Lessons: List<Lesson> = listOf(
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 1: Budgets and Savings"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 2: Insurance"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 3: Mortgages"
        ),
        Lesson(
            R.drawable.tool_stock_market,
            "Lesson 4: Retirement"
        )
    )

    val stateSalesTaxes: Map<String, Double> = mapOf(
        "Alabama" to 4.00,
        "Alaska" to 0.00,
        "Arizona" to 5.60,
        "Arkansas" to 6.50,
        "California" to 7.25,
        "Colorado" to 2.90,
        "Connecticut" to 6.35,
        "Delaware" to 0.00,
        "Florida" to 6.00,
        "Georgia" to 4.00,
        "Hawaii" to 4.00,
        "Idaho" to 6.00,
        "Illinois" to 6.25,
        "Indiana" to 7.00,
        "Iowa" to 6.00,
        "Kansas" to 6.50,
        "Kentucky" to 6.00,
        "Louisiana" to 4.45,
        "Maine" to 5.50,
        "Maryland" to 6.00,
        "Massachusetts" to 6.25,
        "Michigan" to 6.00,
        "Minnesota" to 6.875,
        "Mississippi" to 7.00,
        "Missouri" to 4.225,
        "Montana" to 0.00,
        "Nebraska" to 5.50,
        "Nevada" to 6.85,
        "New Hampshire" to 0.00,
        "New Jersey" to 6.625,
        "New Mexico" to 4.875,
        "New York" to 4.00,
        "North Carolina" to 4.75,
        "North Dakota" to 5.00,
        "Ohio" to 5.75,
        "Oklahoma" to 4.50,
        "Oregon" to 0.00,
        "Pennsylvania" to 6.00,
        "Rhode Island" to 7.00,
        "South Carolina" to 6.00,
        "South Dakota" to 4.20,
        "Tennessee" to 7.00,
        "Texas" to 6.25,
        "Utah" to 6.10,
        "Vermont" to 6.00,
        "Virginia" to 5.30,
        "Washington" to 6.50,
        "West Virginia" to 6.00,
        "Wisconsin" to 5.00,
        "Wyoming" to 4.00
    )
}