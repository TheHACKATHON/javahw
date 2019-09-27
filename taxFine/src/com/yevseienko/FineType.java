package com.yevseienko;

public class FineType {
    private static int Id;
    private int _id;
    private int _taxCodeNumber;
    private String _description;
    private double _minAmount;
    private float _percentOfIncome;

    static {
        Id = Settings.getMaxFineTypeId();
    }

    private FineType(int taxCodeNumber, String description, double minAmount, float percentOfIncome) {
        _id = ++Id;
        Settings.newFineType();

        if (description != null) {
            description = description.trim();
        }

        if (taxCodeNumber > 0 && description != null && !description.isEmpty()) {
            _taxCodeNumber = taxCodeNumber;
            _description = description;
            _minAmount = minAmount;
            _percentOfIncome = percentOfIncome;
        } else {
            throw new IllegalArgumentException("Неверно введены параметры");
        }
    }

    public int getId() {
        return _id;
    }

    public String getDescription() {
        return _description;
    }

    public double getAmount() {
        return _minAmount;
    }

    public double getPercent() {
        return _percentOfIncome;
    }

    public double calcFine(double income) {
        var fine = income / 100 * _percentOfIncome;
        return Math.max(fine, _minAmount);
    }

    // типы штрафов на время теста
    public static FineType[] getTypes() {
        return new FineType[]{F0, F1, F2, F3, F4, F5, F6, F6, F7, F8, F9};
    }

    public static FineType F0 = new FineType(2, "Нарушение налогоплательщиком срока подачи заявления о постановке на " +
            "налоговый учет при отсутствии признаков налогового правонарушения", 5_000, 0);
    public static FineType F1 = new FineType(4, "Нарушение налогоплательщиком срока подачи заявления о постановке на " +
            "учет в налоговом органе на срок более 90 дней", 10_000, 0);
    public static FineType F2 = new FineType(8, "Ведение деятельности налогоплательщиком без постановки на учет в " +
            "налоговом органе", 20_000, 10);
    public static FineType F3 = new FineType(16, "Ведение деятельности налогоплательщиком без постановки на налоговый " +
            "учет в налоговом органе более трех месяцев", 40_000, 20);
    public static FineType F4 = new FineType(32, "Нарушение налогоплательщиком установленного срока представления в " +
            "налоговый орган информации об открытии или закрытии им счета в каком либо банке", 5_000, 0);
    public static FineType F5 = new FineType(64, "Непредставление налогоплательщиком в установленный налоговым " +
            "законодательством срок налоговой декларации при отсутствии признаков налогового правонарушения", 100, 5);
    public static FineType F6 = new FineType(128, "Непредставление налогоплательщиком налоговой декларации в налоговый " +
            "орган в течение более 180 дней по истечении установленного законодательством о налогах и сборах срока", 0, 30);
    public static FineType F7 = new FineType(256, "Грубое нарушение организацией правил учета доходов и расходов и (или) объектов " +
            "налогообложения, если эти деяния совершены в течение одного налогового периода при отсутствии признаков налогового правонарушения", 5_000, 0);
    public static FineType F8 = new FineType(512, "Те же деяния, если они совершены в течение более одного налогового периода", 15_000, 0);
    public static FineType F9 = new FineType(1024, "Те же деяния, если они повлекли за собой занижение налоговой базы", 15_000, 10);

    // todo: убрать хардкод - сериализовать
    // todo: добавить номер из налогового кодекса и сделать статический get(num)
}
